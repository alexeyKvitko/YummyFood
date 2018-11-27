package ru.yummy.food.service.impl;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.food.AppConstants;
import ru.yummy.food.entity.Company;
import ru.yummy.food.entity.MenuEntity;
import ru.yummy.food.entity.MenuItem;
import ru.yummy.food.entity.ParseMenu;
import ru.yummy.food.exception.BusinessLogicException;
import ru.yummy.food.model.CompanyMenu;
import ru.yummy.food.model.MenuEntityModel;
import ru.yummy.food.model.ParseMenuModel;
import ru.yummy.food.model.WeightSizePrice;
import ru.yummy.food.model.enums.EntityStatus;
import ru.yummy.food.repo.*;
import ru.yummy.food.service.ParseService;
import ru.yummy.food.util.AppUtils;
import ru.yummy.food.util.ConvertUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@Service
public class ParseServiceImpl implements ParseService {

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
            menuEntityRepository.updateMenuEntities(parseMenu.getCompanyId(), parseMenu.getCategoryId(), parseMenu.getCategoryId());
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
                menuEntity.setImageUrl( parseMenu.getPrefixUrl() != null ? parseMenu.getPrefixUrl()+imageUrl : imageUrl );
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
    public synchronized CompanyMenu testPage(ParseMenuModel parseMenu) {
        HttpTransport httpTransport = new NetHttpTransport();
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
        String htmlResponse = null;
        HttpRequest request = null;
        try {
            request = requestFactory.buildGetRequest(new GenericUrl(parseMenu.getParseUrl()));
            htmlResponse = request.execute().parseAsString();
        } catch (Exception e) {
            htmlResponse = getByConnection(parseMenu.getParseUrl());
            if (htmlResponse == null) {
                return null;
            }
        }
        htmlResponse = htmlResponse.replace("\n", "").replace("\r", "").replace("\t", "");
        // Убираем заголовок
        if (parseMenu.getTagTrash() != null) {
            int trashIdx = htmlResponse.indexOf(parseMenu.getTagTrash()) + parseMenu.getTagTrash().length();
            htmlResponse = htmlResponse.substring(trashIdx);
        }
        htmlResponse = htmlResponse.substring(htmlResponse.indexOf(parseMenu.getTagEndSection()));
        // Убираем комментарии
        htmlResponse = AppUtils.polish(htmlResponse);
        parseMenu.setHtmlResponse( htmlResponse );
        boolean proceed = true;
        List<MenuEntityModel> menuEntities = new LinkedList<>();
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
            MenuEntityModel menuEntity = new MenuEntityModel();
            menuEntity.setStatus(EntityStatus.NEW.value());
            menuEntity.setName(uniqueName);
            menuEntity.setDisplayName(entityName);
            menuEntity.setDescription(AppUtils.getFieldValue(section, parseMenu.getTagDescription()));
            String imageUrl = AppUtils.getFieldValue(section, parseMenu.getTagImageUrl());
            menuEntity.setImageUrl( parseMenu.getPrefixUrl() != null ? parseMenu.getPrefixUrl()+imageUrl : imageUrl );
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
            menuEntities.add( menuEntity);
        }
        CompanyMenu companyMenu = new CompanyMenu();
        companyMenu.setParseMenu( parseMenu );
        companyMenu.setMenuEntities( menuEntities );
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
                entityPrice = Integer.valueOf(AppUtils.getFieldValue(section, tagPrice).replaceAll(" ", ""));
            } catch (Exception e) {
                return wsp;
            }
            wsp.setPrice(entityPrice);
            if (tagWeight != null) {
                wsp.setWeight(AppUtils.getFieldValue(section, tagWeight));
            }
            if (tagSize != null) {
                wsp.setSize(AppUtils.getFieldValue(section, tagSize));
            }
        }
        return wsp;
    }

    @Override
    public void saveParseModel(ParseMenuModel parseMenuModel) throws BusinessLogicException {
        try {
            ParseMenu parseMenu = parseRepository.findById( parseMenuModel.getId() ).get();
            convertUtils.convertParseMenuModelToEntity( parseMenu, parseMenuModel);
            parseRepository.save( parseMenu );
            parsePage();
        } catch ( Exception e ){
            throw new BusinessLogicException( e.getMessage() );
        }
    }
}
