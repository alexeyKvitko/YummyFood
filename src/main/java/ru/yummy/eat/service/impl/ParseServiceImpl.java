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
import org.springframework.transaction.annotation.Transactional;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.MenuEntity;
import ru.yummy.eat.entity.MenuItem;
import ru.yummy.eat.entity.ParseMenu;
import ru.yummy.eat.entity.UpdateJournal;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.*;
import ru.yummy.eat.model.enums.TestStatus;
import ru.yummy.eat.repo.*;
import ru.yummy.eat.service.ParseService;
import ru.yummy.eat.util.AppUtils;
import ru.yummy.eat.util.ConvertUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

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
    UpdateJournalRepository journalRepository;

    @Autowired
    CompanyServiceImpl companyService;

    @Autowired
    ConvertUtils convertUtils;

    @Autowired
    MailServiceImpl mailService;


    @Override
    @Transactional
    public void parsePage(ParseMenu parseMenu) {
        //Собираем все url
        List<String> parsedUrls =  new LinkedList<>();
        parsedUrls.add( parseMenu.getParseUrl() );
        if( parseMenu.getParseUrlTwo() != null  ){
            parsedUrls.add( parseMenu.getParseUrlTwo() );
        }
        if( parseMenu.getParseUrlThree() != null  ){
            parsedUrls.add( parseMenu.getParseUrlThree() );
        }
        if( parseMenu.getParseUrlFour() != null  ){
            parsedUrls.add( parseMenu.getParseUrlFour() );
        }
        for( String parseUrl :parsedUrls ){
            try {
                HttpTransport httpTransport = new NetHttpTransport();
                HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
                parseMenu.setUpdateResult(AppConstants.PRODUCTION_SUCCESS);
                String htmlResponse = null;
                HttpRequest request = null;
                try {
                    request = requestFactory.buildGetRequest(new GenericUrl(parseUrl));
                    htmlResponse = request.execute().parseAsString();
                } catch (Exception e) {
                    htmlResponse = getByConnection(parseUrl);
                    if (htmlResponse == null) {
                        return;
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
                boolean proceed = true;
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
                    }
                    menuEntity.setName(uniqueName);
                    menuEntity.setDisplayName(entityName);
                    if( parseMenu.getTagDescription() != null &&
                            parseMenu.getTagDescription().trim().length() > 0 ){
                        menuEntity.setDescription(AppUtils.getFieldValue(section, parseMenu.getTagDescription()));
                    }
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
            } catch (Exception e) {
                parseMenu.setUpdateResult(AppConstants.ERROR_PRODUCTION);
                StringBuilder st = new StringBuilder();
                for (int i = e.getStackTrace().length - 2; i < e.getStackTrace().length; i++) {
                    st.append("METHOD '" + e.getStackTrace()[i].getMethodName() + "',LINE NUMBER [" + e.getStackTrace()[i].getLineNumber() + "]; ");
                }
                String description = e.getMessage() + ", Stack trace: " + st.toString();
                parseMenu.setDescription(description.trim().length() > 1999 ?
                        description.trim().substring(0, 1999) : description);
            }

        }

        parseMenu.setLastUpdate(AppUtils.formatDate(AppConstants.UPDATE_DATE_FORMAT, new Date()));
        parseRepository.save(parseMenu);

        return;
    }


    @Override
    public CompanyMenu testPage(ParseMenuModel parseMenu, boolean isUpdateJournal) throws BusinessLogicException {
        LOG.info("******************************************************");
        LOG.info("** TEST");
        LOG.info("** ");
        LOG.info("** " + parseMenu.getParseUrl());
        ParseResult parseResult = new ParseResult();
        HttpTransport httpTransport = new NetHttpTransport();
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
        parseMenu.setBroken(false);
        parseResult.getHtmlResult().setStatus(TestStatus.ERROR.value());
        StringBuilder emptyTags = new StringBuilder();
        UpdateJournal updateJournal = new UpdateJournal();
        updateJournal.setParseUrl(parseMenu.getParseUrl());
        updateJournal.setValue(AppConstants.TEST_SUCCESS);
        parseMenu.setUpdateResult(AppConstants.TEST_SUCCESS);
        //Собираем все url
        List<String> parsedUrls =  new LinkedList<>();
        parsedUrls.add( parseMenu.getParseUrl() );
        if( parseMenu.getParseUrlTwo() != null && parseMenu.getParseUrlTwo().trim().length() > 0 ){
            parsedUrls.add( parseMenu.getParseUrlTwo() );
        }
        if( parseMenu.getParseUrlThree() != null && parseMenu.getParseUrlThree().trim().length() > 0 ){
            parsedUrls.add( parseMenu.getParseUrlThree() );
        }
        if( parseMenu.getParseUrlFour() != null && parseMenu.getParseUrlFour().trim().length() > 0 ){
            parsedUrls.add( parseMenu.getParseUrlFour() );
        }
        StringBuilder sb = new StringBuilder();
        int iterationStep = 0;
        List<MenuEntityModel> menuEntities = new LinkedList<>();
        for(String parseUrl : parsedUrls ){
            String htmlResponse = null;
            HttpRequest request = null;
            try {
                request = requestFactory.buildGetRequest(new GenericUrl( parseUrl ));
                htmlResponse = request.execute().parseAsString();
            } catch (Exception e) {
                htmlResponse = getByConnection( parseUrl );
                if (htmlResponse == null) {
                    parseResult.getHtmlResult().setStatus(TestStatus.ERROR.value());
                    parseMenu.setBroken(true);
                    parseResult.setMessage(e.getMessage());
                }
            }
            if (htmlResponse != null) {
                parseResult.getHtmlResult().setStatus(TestStatus.PASSED.value());

                try {
                    LOG.info("** GET FULL HTML ...");
                    htmlResponse = htmlResponse.replace("\n", "").replace("\r", "").replace("\t", "");
                    LOG.info("** Ok");
                    LOG.info("**");
                    // Убираем заголовок
                    parseResult.getHtmlClean().setStatus(TestStatus.ERROR.value());
                    LOG.info("** REMOVE THE HEADINGS ...");
                    if (parseMenu.getTagTrash() != null) {
                        int trashIdx = htmlResponse.indexOf(parseMenu.getTagTrash()) + parseMenu.getTagTrash().length();
                        htmlResponse = htmlResponse.substring(trashIdx);
                    }
                    parseResult.getHtmlClean().setStatus(TestStatus.PASSED.value());
                    LOG.info("** Ok");
                    LOG.info("**");
                    LOG.info("** CLEAN COMMENTS ...");
                    parseResult.getHtmlTag().setStatus(TestStatus.ERROR.value());
                    htmlResponse = htmlResponse.substring(htmlResponse.indexOf(parseMenu.getTagEndSection()));
                    // Убираем комментарии
                    htmlResponse = AppUtils.polish(htmlResponse);
                    if( parseMenu.getId() == 21){
                        System.out.println("Html Response: "+htmlResponse);
                    }
                    parseResult.getHtmlTag().setStatus(TestStatus.PASSED.value());
                    LOG.info("** Ok");
                    LOG.info("**");
                    boolean proceed = true;
                    while (proceed) {
                        parseResult.setStep(iterationStep);
                        StringBuilder sbError = new StringBuilder();
                        sbError.append("Product [" + iterationStep + "]");
                        //Укорачиваем
                        LOG.info("** SECTION [" + iterationStep + "]");
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
                        parseResult.getProductName().setStatus(TestStatus.ERROR.value());
                        parseResult.getProductName().setiStep(iterationStep - 1);
                        String entityName = AppUtils.getFieldValue(section, parseMenu.getTagName());
                        LOG.info("** Ok");
                        LOG.info("**");
                        parseResult.getProductName().setStatus(TestStatus.PASSED.value());
                        if (entityName == null) {
                            proceed = false;
                            if (iterationStep == 0) {
                                throw new BusinessLogicException("Не найдено имя блюда, процесс завершен");
                            } else {
                                break;
                            }
                        }
                        if (parseMenu.getTagName() != null && (entityName == null || entityName.trim().length() == 0)) {
                            emptyTags.append("[" + iterationStep + "] tag_name;");
                        }
                        parseResult.setSection(section);
                        sb.append(AppConstants.SECTION).append((iterationStep + 1)).append("]").append(section);
                        String uniqueName = entityName.toUpperCase().replace(" ", "_") + "_" + parseMenu.getCompanyId().toString();
                        MenuEntityModel menuEntity = new MenuEntityModel();
                        menuEntity.setName(uniqueName);
                        menuEntity.setDisplayName(entityName);
                        LOG.info("** GET DESCRIPTION ...");
                        parseResult.getProductDesc().setStatus(TestStatus.ERROR.value());
                        parseResult.getProductDesc().setiStep(iterationStep);
                        if( parseMenu.getTagDescription() != null &&
                                parseMenu.getTagDescription().trim().length() > 0 ){
                            menuEntity.setDescription(AppUtils.getFieldValue(section, parseMenu.getTagDescription()));
                        }
                        parseResult.getProductDesc().setStatus(TestStatus.PASSED.value());
                        if (parseMenu.getTagDescription() != null && (menuEntity.getDescription() == null ||
                                menuEntity.getDescription().trim().length() == 0)) {
                            emptyTags.append("[" + iterationStep + "] tag_description;");
                        }
                        LOG.info("** Ok");
                        LOG.info("**");
                        LOG.info("** GET THE IMAGE ...");
                        parseResult.getProductImg().setStatus(TestStatus.ERROR.value());
                        parseResult.getProductImg().setiStep(iterationStep);
                        String imageUrl = AppUtils.getFieldValue(section, parseMenu.getTagImageUrl());
                        parseResult.getProductImg().setStatus(TestStatus.PASSED.value());
                        if (parseMenu.getTagImageUrl() != null && (imageUrl == null || imageUrl.trim().length() == 0)) {
                            emptyTags.append("[" + iterationStep + "] tag_img_url;");
                        }
                        LOG.info("** Ok");
                        LOG.info("**");
                        menuEntity.setImageUrl(parseMenu.getPrefixUrl() != null ? parseMenu.getPrefixUrl() + imageUrl : imageUrl);
                        // wsp One
                        LOG.info("** GET WSP - 1 (Weight, Size, Price)  ...");
                        parseResult.getWspOne().setStatus(TestStatus.ERROR.value());
                        parseResult.getWspOne().setiStep(iterationStep);
                        WeightSizePrice wspOne = getWSP(section, parseMenu.getTagWeightOne(),
                                parseMenu.getTagSizeOne(), parseMenu.getTagPriceOne());
                        menuEntity.setWeightOne(wspOne.getWeight());
                        menuEntity.setSizeOne(wspOne.getSize());
                        menuEntity.setPriceOne(wspOne.getPrice());
                        parseResult.getWspOne().setStatus(TestStatus.PASSED.value());
                        if (parseMenu.getTagPriceOne() != null && wspOne.getPrice() == null) {
                            emptyTags.append("[" + iterationStep + "] tag_price_one;");
                        }
                        LOG.info("** Ok");
                        LOG.info("**");

                        // wsp Two
                        LOG.info("** GET WSP - 2  ...");
                        parseResult.getWspTwo().setStatus(TestStatus.ERROR.value());
                        parseResult.getWspTwo().setiStep(iterationStep);
                        WeightSizePrice wspTwo = getWSP(section, parseMenu.getTagWeightTwo(),
                                parseMenu.getTagSizeTwo(), parseMenu.getTagPriceTwo());
                        menuEntity.setWeightTwo(wspTwo.getWeight());
                        menuEntity.setSizeTwo(wspTwo.getSize());
                        menuEntity.setPriceTwo(wspTwo.getPrice());
                        parseResult.getWspTwo().setStatus(TestStatus.PASSED.value());
                        if (parseMenu.getTagPriceTwo() != null && wspTwo.getPrice() == null) {
                            emptyTags.append("[" + iterationStep + "] tag_price_two;");
                        }
                        LOG.info("** Ok");
                        LOG.info("**");

                        // wsp Three
                        LOG.info("** GET WSP - 3  ...");
                        parseResult.getWspThree().setStatus(TestStatus.ERROR.value());
                        parseResult.getWspThree().setiStep(iterationStep);
                        WeightSizePrice wspThree = getWSP(section, parseMenu.getTagWeightThree(),
                                parseMenu.getTagSizeThree(), parseMenu.getTagPriceThree());
                        menuEntity.setWeightThree(wspThree.getWeight());
                        menuEntity.setSizeThree(wspThree.getSize());
                        menuEntity.setPriceThree(wspThree.getPrice());
                        parseResult.getWspThree().setStatus(TestStatus.PASSED.value());
                        if (parseMenu.getTagPriceThree() != null && wspThree.getPrice() == null) {
                            emptyTags.append("[" + iterationStep + "] tag_price_three;");
                        }
                        LOG.info("** Ok");
                        LOG.info("**");
                        // wsp Four
                        LOG.info("** GET WSP - 4  ...");
                        parseResult.getWspFour().setStatus(TestStatus.ERROR.value());
                        parseResult.getWspFour().setiStep(iterationStep);
                        WeightSizePrice wspFour = getWSP(section, parseMenu.getTagWeightFour(),
                                parseMenu.getTagSizeFour(), parseMenu.getTagPriceFour());
                        menuEntity.setWeightFour(wspFour.getWeight());
                        menuEntity.setSizeFour(wspFour.getSize());
                        menuEntity.setPriceFour(wspFour.getPrice());
                        parseResult.getWspFour().setStatus(TestStatus.PASSED.value());
                        if (parseMenu.getTagPriceFour() != null && wspFour.getPrice() == null) {
                            emptyTags.append("[" + iterationStep + "] tag_price_four;");
                        }
                        LOG.info("** Ok");
                        LOG.info("**");
                        menuEntities.add(menuEntity);
                        iterationStep++;
                    }
                } catch (Exception e) {
                    LOG.error("** EXCEPTION: " + e.getMessage());
                    e.printStackTrace();
                    StringBuilder st = new StringBuilder();
                    for (int i = e.getStackTrace().length - 2; i < e.getStackTrace().length; i++) {
                        st.append("METHOD '" + e.getStackTrace()[i].getMethodName() + "',LINE NUMBER [" + e.getStackTrace()[i].getLineNumber() + "]; ");
                    }
                    LOG.error("** " + st.toString());
                    parseMenu.setBroken(true);
                    parseMenu.setUpdateResult(AppConstants.ERROR);
                    updateJournal.setValue(AppConstants.ERROR);
                    String description = e.getMessage() + ", Stack trace: " + st.toString();
                    parseMenu.setDescription(description.trim().length() > 1999 ?
                            description.trim().substring(0, 1999) : description);
                    parseResult.setMessage(e.getMessage());
                    parseResult.setStackTrace(st.toString());
                }
            }
        }
        if (isUpdateJournal) {
            emptyTags.append(" Total dishes: "+iterationStep );
            updateJournal.setTagName(emptyTags.toString());
            updateJournal.setUpdateDate( AppUtils.formatDate(AppConstants.UPDATE_DATE_FORMAT, new Date()) );
            journalRepository.save(updateJournal);
        }
        CompanyMenu companyMenu = new CompanyMenu();
        parseMenu.setParseResult(parseResult);
        parseMenu.setHtmlResponse(sb.toString());
        companyMenu.setParseMenu(parseMenu);
        companyMenu.setMenuEntities(parseMenu.isBroken() ? null : menuEntities);
        LOG.info("**");
        LOG.info("** FINISH TEST");
        LOG.info("*************************");
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
                LOG.info("** PRICE: " + price);
                entityPrice = Integer.valueOf(price);
            } catch (Exception e) {
                return wsp;
            }
            wsp.setPrice(entityPrice);

            if (tagWeight != null) {
                String weight = AppUtils.getFieldValue(section, tagWeight);
                if (weight != null && tagWeight.indexOf(AppConstants.INLINE_TAG_VALUE) == -1) {
                    weight = weight.toLowerCase().replace("грамм", "").replace("гр.", "").replace("г.", "").replace("г", "").replace(".", "");
                    weight = weight + " гр.";
                }
                LOG.info("** WEIGHT: " + weight);
                if ( weight != null ){
                    weight = weight.length() < 20 ? weight : weight.substring(0,19);
                }
                wsp.setWeight( weight );
            }

            if (tagSize != null) {
                String size = AppUtils.getFieldValue(section, tagSize);
                if (size != null && tagSize.indexOf(AppConstants.INLINE_TAG_VALUE) == -1) {
                    size = size.toLowerCase().replace("см.", "").replace("см", "").replace("с", "").replace(".", "");
                    size = size + " см.";
                }
                LOG.info("** SIZE: " + size);
                if (size != null){
                  size = size.length() < 20 ? size : size.substring(0,19);
                }
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
            parsePage( parseMenu );
        } catch (Exception e) {
            throw new BusinessLogicException(e.getMessage());
        }
    }

    @Override
    public ParseMenuModel copyParseData(CopyParseData copyParseData) throws BusinessLogicException {
        ParseMenuModel targetMenuModel = null;
        try {
            ParseMenu sourceMenu = parseRepository.findParseMenuByCompanyIdAndTypeIdAndCategoryId(copyParseData.getCompanyId(),
                    copyParseData.getFromMenuTypeId(), copyParseData.getFromMenuCategoryId());
            ParseMenu targetMenu = parseRepository.findParseMenuByCompanyIdAndTypeIdAndCategoryId(copyParseData.getCompanyId(),
                    copyParseData.getToMenuTypeId(), copyParseData.getToMenuCategoryId());

            targetMenu.setPrefixUrl(sourceMenu.getPrefixUrl());
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

            parseRepository.save(targetMenu);
            targetMenuModel = convertUtils.convertParseMenuToModel(targetMenu);

        } catch (Exception e) {
            throw new BusinessLogicException(e.getMessage());
        }
        return targetMenuModel;
    }

    public void scheduledTestParseMenu() {
        List<ParseMenu> parseMenus = parseRepository.findAllByLastUpdateIsNull();
        List<String> successResult = new LinkedList<>();
        int idx = 0;
        ExecutorService tasksPool = Executors.newFixedThreadPool(10);
        try {
            List<Future<String>> testing = new LinkedList<>();
            for (int menuIndex = 0; menuIndex < parseMenus.size(); menuIndex++) {
                TestParsing testParsing = new TestParsing(parseMenus.get(menuIndex));
                Future<String> parsingResult = tasksPool.submit(testParsing);
                testing.add(parsingResult);
                idx++;
            }
            while (successResult.size() < parseMenus.size()) {
                for (Future<String> result : testing) {
                    try {
                        String value = result.get();
                        successResult.add(value);
                    } catch (InterruptedException | ExecutionException ex) {
                        LOG.error(" Ошибка в потоке: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Тестирование провалилось " + e.getMessage());
        } finally {
            tasksPool.shutdown();
        }
        scheduledProductionParseMenu();
    }

    @Override
    public void scheduledProductionParseMenu() {
        boolean success = true;
        List<ParseMenu> parseMenus = parseRepository.findAllByUpdateResult(AppConstants.TEST_SUCCESS);
        List<String> productionResult = new LinkedList<>();
        int idx = 0;
        ExecutorService tasksPool = Executors.newFixedThreadPool(10);
        try {
            List<Future<String>> parsingCalls = new LinkedList<>();
            for (int menuIndex = 0; menuIndex < parseMenus.size(); menuIndex++) {
                ProductionParsing productionParsing = new ProductionParsing(parseMenus.get(menuIndex));
                Future<String> parsingResult = tasksPool.submit(  productionParsing );
                parsingCalls.add( parsingResult );
                idx++;
            }
            while (productionResult.size() < parseMenus.size()) {
                for (Future<String> result : parsingCalls) {
                    try {
                        String value = result.get();
                        productionResult.add(value);
                    } catch (InterruptedException | ExecutionException ex) {
                        LOG.error(" Ошибка в потоке: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            success = false;
            LOG.error("Exception in method scheduledProductionParseMenu " + e.getMessage());
        } finally {
            tasksPool.shutdown();
        }
        StringBuilder sb = new StringBuilder();
        if( !success ){
            sb.append("<H2><b>ПОЛНЫЙ ПРОВАЛ</b></H2>");
            sb.append("<H2><b>СМОТРЕТЬ ЛОГИ</b></H2>");
        } else {

            List<String> errorPages = menuEntityRepository.getPageWithErrors();
            if( errorPages == null || errorPages.size() == 0){
                sb.append( "<H2><b>ОБНОВЛЕНО СТРАНИЦ БЕЗ ОШИБОК: "+parseMenus.size()+"</b></H2>" );
            } else {
                sb.append( "<H2><b>СТРАНИЦЫ С ОШИБКАМИ:</b></H2>" );
                sb.append("<ul>");
                for(String page: errorPages){
                    sb.append( "<li>"+page+"</li>" );
                }
                sb.append("</ul>");
            }
        }
        mailService.sendEmail( "Обновление базы предприятий", sb.toString() );
    }

    private class TestParsing implements Callable<String> {

        private ParseMenu parseMenu;

        public TestParsing(ParseMenu parseMenu) {
            this.parseMenu = parseMenu;
        }

        @Override
        public String call() throws Exception {
            String result = AppConstants.TEST_SUCCESS;
            try {
                parseMenu.setLastUpdate(AppUtils.formatDate(AppConstants.UPDATE_DATE_FORMAT, new Date()));
                CompanyMenu companyMenu = testPage(convertUtils.convertParseMenuToModel(parseMenu), true);
                convertUtils.convertParseMenuModelToEntity(parseMenu, companyMenu.getParseMenu());
            } catch (Exception e) {
                result = AppConstants.ERROR;
                parseMenu.setUpdateResult(AppConstants.ERROR);
                parseMenu.setDescription(e.getMessage());
            }
            parseMenu.setProcessed(0);
            parseRepository.save(parseMenu);
            return result;
        }
    }

    private class ProductionParsing implements Callable<String> {

        private ParseMenu parseMenu;

        public ProductionParsing(ParseMenu parseMenu) {
            this.parseMenu = parseMenu;
        }

        @Override
        public String call() throws Exception {
            String result = AppConstants.PRODUCTION_SUCCESS;
            try {
                companyService.deleteCompanyMenuEntities(parseMenu.getCompanyId(), parseMenu.getTypeId(), parseMenu.getCategoryId());
                parsePage( parseMenu );
            } catch (Exception e) {
                result = AppConstants.ERROR;
                parseMenu.setUpdateResult(AppConstants.ERROR);
                parseMenu.setDescription(e.getMessage());
            }
            parseMenu.setProcessed(0);
            parseRepository.save(parseMenu);
            return result;
        }
    }


}
