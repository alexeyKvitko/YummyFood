package ru.yummy.eat.service.impl;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.MenuEntity;
import ru.yummy.eat.entity.MenuItem;
import ru.yummy.eat.entity.ParseMenu;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.*;
import ru.yummy.eat.model.enums.EntityStatus;
import ru.yummy.eat.repo.*;
import ru.yummy.eat.service.ParseService;
import ru.yummy.eat.util.AppUtils;
import ru.yummy.eat.util.ConvertUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@Service
public class ParseServiceImpl implements ParseService {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    ParseMenuRepository parseRepository;

    @Autowired
    MenuEntityRepository menuEntityRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    ConvertUtils convertUtils;

    @Override
    public synchronized void parsePage() {
        List<ParseMenu> parseMenus = parseRepository.findAllByProcessed(AppConstants.PROCEED);
        HttpTransport httpTransport = new NetHttpTransport();
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
        for (ParseMenu parseMenu : parseMenus) {
            String htmlResponse = null;
            HttpRequest request = null;
            try {
                request = requestFactory.buildGetRequest(new GenericUrl(parseMenu.getParseUrl()));
                htmlResponse = request.execute().parseAsString();
            } catch (Exception e) {
                htmlResponse = getByConnection(parseMenu.getParseUrl());
                if (htmlResponse == null) {
                    break;
                }
            }
            htmlResponse = htmlResponse.replace("\n", "").replace("\r", "").replace("\t", "");
            //ОБНОВЛЯЕМ СТАТУС
            menuEntityRepository.deleteMenuEntities( parseMenu.getCompanyId(), parseMenu.getCategoryId(), parseMenu.getCategoryId());
            menuItemRepository.deleteMenuItems( parseMenu.getCompanyId(), parseMenu.getCategoryId(), parseMenu.getCategoryId());
            // Убираем заголовок
            if (parseMenu.getTagTrash() != null) {
                int trashIdx = htmlResponse.indexOf(parseMenu.getTagTrash()) + parseMenu.getTagTrash().length();
                htmlResponse = htmlResponse.substring(trashIdx);
            }
            htmlResponse = htmlResponse.substring(htmlResponse.indexOf(parseMenu.getTagEndSection()));
            // Убираем комментарии
            htmlResponse = AppUtils.polish(htmlResponse);
            boolean proceed = true;
            List<MenuEntity> menuEntities = new LinkedList<>();
            while (proceed) {
                //Укорачиваем
                int endSectionIdx = htmlResponse.indexOf(parseMenu.getTagEndSection());
                String section = null;
                if (endSectionIdx > -1) {
                    section = AppUtils.getSection(htmlResponse, parseMenu.getTagEndSection());
                    htmlResponse = htmlResponse.substring(section.length());
                } else {
                    section = htmlResponse;
                }
                //Получаем данные
                String entityName = AppUtils.getFieldValue(section, parseMenu.getTagName());
                if (entityName == null) {
                    proceed = false;
                    break;
                }
                String uniqueName = entityName.toUpperCase().replace(" ", "_") + "_" + parseMenu.getCompanyId().toString();
                MenuEntity menuEntity = menuEntityRepository.findByName(uniqueName);
                if (menuEntity == null) {
                    menuEntity = new MenuEntity();
                    menuEntity.setStatus(EntityStatus.NEW.value());
                } else {
                    menuEntity.setStatus(EntityStatus.UPDATED.value());
                }
                menuEntity.setName(uniqueName);
                menuEntity.setDisplayName(entityName);
                menuEntity.setDescription(AppUtils.getFieldValue(section, parseMenu.getTagDescription()));
                String imageUrl = AppUtils.getFieldValue(section, parseMenu.getTagImageUrl());
                menuEntity.setImageUrl(parseMenu.getPrefixUrl() != null ? parseMenu.getPrefixUrl() + imageUrl : imageUrl);
                // wsp One
                WeightSizePrice wspOne = getWSP(section, parseMenu.getTagWeightOne(),
                        parseMenu.getTagSizeOne(), parseMenu.getTagPriceOne());
                menuEntity.setWeightOne(wspOne.getWeight());
                menuEntity.setSizeOne(wspOne.getSize());
                menuEntity.setPriceOne(wspOne.getPrice());

                // wsp Two
                WeightSizePrice wspTwo = getWSP(section, parseMenu.getTagWeightTwo(),
                        parseMenu.getTagSizeTwo(), parseMenu.getTagPriceTwo());
                menuEntity.setWeightTwo(wspTwo.getWeight());
                menuEntity.setSizeTwo(wspTwo.getSize());
                menuEntity.setPriceTwo(wspTwo.getPrice());

                // wsp Three
                WeightSizePrice wspThree = getWSP(section, parseMenu.getTagWeightThree(),
                        parseMenu.getTagSizeThree(), parseMenu.getTagPriceThree());
                menuEntity.setWeightThree(wspThree.getWeight());
                menuEntity.setSizeThree(wspThree.getSize());
                menuEntity.setPriceThree(wspThree.getPrice());

                // wsp Four
                WeightSizePrice wspFour = getWSP(section, parseMenu.getTagWeightFour(),
                        parseMenu.getTagSizeFour(), parseMenu.getTagPriceFour());
                menuEntity.setWeightFour(wspFour.getWeight());
                menuEntity.setSizeFour(wspFour.getSize());
                menuEntity.setPriceFour(wspFour.getPrice());

                menuEntityRepository.save(menuEntity);
                Integer itemId = menuItemRepository.getMenuItemId(parseMenu.getCompanyId(), parseMenu.getTypeId(),
                        parseMenu.getCategoryId(), menuEntity.getId());
                if (itemId == null) {
                    MenuItem menuItem = new MenuItem();
                    menuItem.setCompanyId(parseMenu.getCompanyId());
                    menuItem.setTypeId(parseMenu.getTypeId());
                    menuItem.setCategoryId(parseMenu.getCategoryId());
                    menuItem.setEntityId(menuEntity.getId());
                    menuItemRepository.save(menuItem);
                }
            }
            parseMenu.setProcessed(AppConstants.PROCESSED);
            parseRepository.save(parseMenu);
        }

        return;
    }


    @Override
    public synchronized CompanyMenu testPage(ParseMenuModel parseMenu) throws BusinessLogicException {
        LOG.info("******************************************************");
        LOG.info("** TEST");
        LOG.info("** ");
        LOG.info("** "+parseMenu.getParseUrl());
        HttpTransport httpTransport = new NetHttpTransport();
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
        String htmlResponse = null;
        HttpRequest request = null;
        parseMenu.setBroken( false );
        try {
            request = requestFactory.buildGetRequest(new GenericUrl(parseMenu.getParseUrl()));
            htmlResponse = request.execute().parseAsString();
        } catch (Exception e) {
            htmlResponse = getByConnection(parseMenu.getParseUrl());
            if (htmlResponse == null) {
                return null;
            }
        }
        List<MenuEntityModel> menuEntities = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        try {
            LOG.info("** GET FULL HTML ...");
            htmlResponse = htmlResponse.replace("\n", "").replace("\r", "").replace("\t", "");
            LOG.info("** Ok");
            LOG.info("**");
            // Убираем заголовок
            LOG.info("** REMOVE THE HEADINGS ...");
            if (parseMenu.getTagTrash() != null) {
                int trashIdx = htmlResponse.indexOf(parseMenu.getTagTrash()) + parseMenu.getTagTrash().length();
                htmlResponse = htmlResponse.substring(trashIdx);
            }
            LOG.info("** Ok");
            LOG.info("**");
            LOG.info("** CLEAN COMMENTS ...");
            htmlResponse = htmlResponse.substring(htmlResponse.indexOf(parseMenu.getTagEndSection()));
            // Убираем комментарии
            htmlResponse = AppUtils.polish(htmlResponse);
            LOG.info("** Ok");
            LOG.info("**");
            boolean proceed = true;
            int count = 0;
            while (proceed) {
                //Укорачиваем
                LOG.info("** SECTION ["+count+"]");
                LOG.info("** ACCESSING ...");
                int endSectionIdx = htmlResponse.indexOf(parseMenu.getTagEndSection());
                String section = null;
                if (endSectionIdx > -1) {
                    section = AppUtils.getSection(htmlResponse, parseMenu.getTagEndSection());
                    htmlResponse = htmlResponse.substring(section.length());
                } else {
                    section = htmlResponse;
                }
                LOG.info("** Ok");
                LOG.info("**");
                //Получаем данные
                LOG.info("** PRODUCT NAME ...");
                String entityName = AppUtils.getFieldValue(section, parseMenu.getTagName());
                LOG.info("** Ok");
                LOG.info("**");
                if (entityName == null) {
                    proceed = false;
                    if (count == 0) {
                        throw new BusinessLogicException("Не найдено имя блюда, процесс завершен");
                    } else {
                        break;
                    }
                }
                parseMenu.setErrorSection( section );
                sb.append(AppConstants.SECTION).append((count+1)).append("]").append(section);
                String uniqueName = entityName.toUpperCase().replace(" ", "_") + "_" + parseMenu.getCompanyId().toString();
                MenuEntityModel menuEntity = new MenuEntityModel();
                menuEntity.setStatus(EntityStatus.NEW.value());
                menuEntity.setName(uniqueName);
                menuEntity.setDisplayName(entityName);
                LOG.info("** GET DESCRIPTION ...");
                menuEntity.setDescription(AppUtils.getFieldValue(section, parseMenu.getTagDescription()));
                LOG.info("** Ok");
                LOG.info("**");
                LOG.info("** GET THE IMAGE ...");
                String imageUrl = AppUtils.getFieldValue(section, parseMenu.getTagImageUrl());
                LOG.info("** Ok");
                LOG.info("**");
                menuEntity.setImageUrl(parseMenu.getPrefixUrl() != null ? parseMenu.getPrefixUrl() + imageUrl : imageUrl);
                // wsp One
                LOG.info("** GET WSP - 1 (Weight, Size, Price)  ...");
                WeightSizePrice wspOne = getWSP(section, parseMenu.getTagWeightOne(),
                        parseMenu.getTagSizeOne(), parseMenu.getTagPriceOne());
                menuEntity.setWeightOne(wspOne.getWeight());
                menuEntity.setSizeOne(wspOne.getSize());
                menuEntity.setPriceOne(wspOne.getPrice());
                LOG.info("** Ok");
                LOG.info("**");

                // wsp Two
                LOG.info("** GET WSP - 2  ...");
                WeightSizePrice wspTwo = getWSP(section, parseMenu.getTagWeightTwo(),
                        parseMenu.getTagSizeTwo(), parseMenu.getTagPriceTwo());
                menuEntity.setWeightTwo(wspTwo.getWeight());
                menuEntity.setSizeTwo(wspTwo.getSize());
                menuEntity.setPriceTwo(wspTwo.getPrice());
                LOG.info("** Ok");
                LOG.info("**");

                // wsp Three
                LOG.info("** GET WSP - 3  ...");
                WeightSizePrice wspThree = getWSP(section, parseMenu.getTagWeightThree(),
                        parseMenu.getTagSizeThree(), parseMenu.getTagPriceThree());
                menuEntity.setWeightThree(wspThree.getWeight());
                menuEntity.setSizeThree(wspThree.getSize());
                menuEntity.setPriceThree(wspThree.getPrice());
                LOG.info("** Ok");
                LOG.info("**");
                // wsp Four
                LOG.info("** GET WSP - 4  ...");
                WeightSizePrice wspFour = getWSP(section, parseMenu.getTagWeightFour(),
                        parseMenu.getTagSizeFour(), parseMenu.getTagPriceFour());
                menuEntity.setWeightFour(wspFour.getWeight());
                menuEntity.setSizeFour(wspFour.getSize());
                menuEntity.setPriceFour(wspFour.getPrice());
                LOG.info("** Ok");
                LOG.info("**");
                menuEntities.add(menuEntity);
                count++;
            }
        } catch (Exception e) {
            LOG.error("** EXCEPTION: "+e.getMessage() );
            LOG.error("** STACK TRACE: "+e.getMessage() );
            StringBuilder st = new StringBuilder();
            for( StackTraceElement str: e.getStackTrace() ){
                st.append("METHOD '"+str.getMethodName()+"',LINE NUMBER ["+str.getLineNumber()+"]; ");
            }
            LOG.error("** "+st.toString() );
            parseMenu.setBroken( true );
            parseMenu.setErrorMsg( e.getMessage() );
        }
        CompanyMenu companyMenu = new CompanyMenu();
        parseMenu.setHtmlResponse( sb.toString() );
        companyMenu.setParseMenu(parseMenu);
        companyMenu.setMenuEntities(parseMenu.isBroken() ? null : menuEntities);
        LOG.info("**" );
        LOG.info("** FINISH TEST" );
        LOG.info("*************************" );
        return companyMenu;
    }

    private String getByConnection(String path) {
        URL url = null;
        InputStream fis = null;
        HttpURLConnection conn = null;
        String htmlResponse = null;
        try {
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36 OPR/55.0.2994.61");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("cookie", "\"PHPSESSID=67ec6f35bb02319ecfa1ed39ac8a9dc7; BITRIX_SM_GUEST_ID=1139945; _ym_uid=1541056815363914308; _ym_d=1541056815; _ym_isad=2; BX_USER_ID=7166c070211ba99c47b371acf9547c0d; BITRIX_SM_LAST_VISIT=01.11.2018+11%3A28%3A14; BITRIX_SM_LAST_ADV=5; _ym_visorc_3081571=w\"");
            conn.connect();
            fis = conn.getInputStream();
            htmlResponse = IOUtils.toString(fis, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return htmlResponse;
    }

    private WeightSizePrice getWSP(String section, String tagWeight, String tagSize, String tagPrice) {
        WeightSizePrice wsp = new WeightSizePrice();
        if (tagPrice != null) {
            Integer entityPrice = 0;
            try {
                String price = AppUtils.getFieldValue(section, tagPrice).replaceAll(" ", "");
                LOG.info("** PRICE: "+price);
                entityPrice = Integer.valueOf(price);
            } catch (Exception e) {
                return wsp;
            }
            wsp.setPrice(entityPrice);
            if (tagWeight != null) {
                String weight = AppUtils.getFieldValue(section, tagWeight);
                LOG.info("** WEIGHT: "+weight);
                wsp.setWeight( weight );
            }
            if (tagSize != null) {
                String size = AppUtils.getFieldValue(section, tagSize);
                LOG.info("** SIZE: "+size);
                wsp.setSize( size );
            }
        }
        return wsp;
    }

    @Override
    public void saveParseModel(ParseMenuModel parseMenuModel) throws BusinessLogicException {
        try {
            ParseMenu parseMenu = parseRepository.findById(parseMenuModel.getId()).get();
            convertUtils.convertParseMenuModelToEntity(parseMenu, parseMenuModel);
            parseRepository.save(parseMenu);
            parsePage();
        } catch (Exception e) {
            throw new BusinessLogicException(e.getMessage());
        }
    }

    @Override
    public ParseMenuModel copyParseData(CopyParseData copyParseData) throws BusinessLogicException {
        ParseMenuModel targetMenuModel =  null;
        try {
            ParseMenu sourceMenu = parseRepository.findParseMenuByCompanyIdAndTypeIdAndCategoryId( copyParseData.getCompanyId(),
                    copyParseData.getFromMenuTypeId(), copyParseData.getFromMenuCategoryId() );
            ParseMenu targetMenu = parseRepository.findParseMenuByCompanyIdAndTypeIdAndCategoryId( copyParseData.getCompanyId(),
                    copyParseData.getToMenuTypeId(), copyParseData.getToMenuCategoryId() );

            targetMenu.setPrefixUrl( sourceMenu.getPrefixUrl() );
            targetMenu.setTagTrash(sourceMenu.getTagTrash());
            targetMenu.setTagEndSection(sourceMenu.getTagEndSection());
            targetMenu.setTagName(sourceMenu.getTagName());
            targetMenu.setTagDescription(sourceMenu.getTagDescription());
            targetMenu.setTagImageUrl(sourceMenu.getTagImageUrl());
            targetMenu.setTagWeightOne(sourceMenu.getTagWeightOne());
            targetMenu.setTagSizeOne(sourceMenu.getTagSizeOne());
            targetMenu.setTagPriceOne(sourceMenu.getTagPriceOne());
            targetMenu.setTagWeightTwo(sourceMenu.getTagWeightTwo());
            targetMenu.setTagSizeTwo(sourceMenu.getTagSizeTwo());
            targetMenu.setTagPriceTwo(sourceMenu.getTagPriceTwo());
            targetMenu.setTagWeightThree(sourceMenu.getTagWeightThree());
            targetMenu.setTagSizeThree(sourceMenu.getTagSizeThree());
            targetMenu.setTagPriceThree(sourceMenu.getTagPriceThree());
            targetMenu.setTagWeightFour(sourceMenu.getTagWeightFour());
            targetMenu.setTagSizeFour(sourceMenu.getTagSizeFour());
            targetMenu.setTagPriceFour(sourceMenu.getTagPriceFour());
            targetMenu.setProcessed(sourceMenu.getProcessed());

            parseRepository.save( targetMenu );
            targetMenuModel = convertUtils.convertParseMenuToModel(targetMenu );

        } catch (Exception e) {
            throw new BusinessLogicException(e.getMessage());
        }
        return targetMenuModel;
    }
    
}
