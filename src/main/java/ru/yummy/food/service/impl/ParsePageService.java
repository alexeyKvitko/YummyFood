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
import ru.yummy.food.entity.MenuEntity;
import ru.yummy.food.entity.MenuItem;
import ru.yummy.food.entity.ParseMenu;
import ru.yummy.food.model.WeightSizePrice;
import ru.yummy.food.model.enums.EntityStatus;
import ru.yummy.food.repo.CityRepository;
import ru.yummy.food.repo.MenuEntityRepository;
import ru.yummy.food.repo.MenuItemRepository;
import ru.yummy.food.repo.ParseMenuRepository;
import ru.yummy.food.util.AppUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@Service
public class ParsePageService extends ParseServiceImpl {

    @Autowired
    ParseMenuRepository parseRepository;

    @Autowired
    MenuEntityRepository menuEntityRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    CityRepository cityRepository;

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
//                HttpHeaders headers = new HttpHeaders();
//                headers.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36 OPR/55.0.2994.61");
//                headers.setAcceptEncoding("UTF-8");
//                headers.setCookie("PHPSESSID=67ec6f35bb02319ecfa1ed39ac8a9dc7; BITRIX_SM_GUEST_ID=1139945; _ym_uid=1541056815363914308; _ym_d=1541056815; _ym_isad=2; BX_USER_ID=7166c070211ba99c47b371acf9547c0d; BITRIX_SM_LAST_VISIT=01.11.2018+11%3A28%3A14; BITRIX_SM_LAST_ADV=5; _ym_visorc_3081571=w");

//                request.setHeaders( headers );
                htmlResponse = request.execute().parseAsString();
            } catch ( Exception e ) {
                htmlResponse = getByConnection( parseMenu.getParseUrl() );
                if ( htmlResponse == null ){
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
                menuEntity.setImageUrl(AppUtils.getFieldValue(section, parseMenu.getTagImageUrl()));
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

    private String  getByConnection( String path ){
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
        } catch (Exception e){
             e.printStackTrace();
        }
        finally {
            conn.disconnect();
            if ( fis != null ){
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
                entityPrice = Integer.valueOf(AppUtils.getFieldValue(section, tagPrice).replaceAll(" ",""));
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

}
