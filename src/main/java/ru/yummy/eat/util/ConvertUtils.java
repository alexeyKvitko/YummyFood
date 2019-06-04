package ru.yummy.eat.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.*;
import ru.yummy.eat.model.*;
import ru.yummy.eat.repo.CityRepository;
import ru.yummy.eat.repo.CompanyRepository;
import ru.yummy.eat.repo.MenuEntityRepository;

import java.util.*;

@Component
public class ConvertUtils {

    @Autowired
    CityRepository cityRepo;

    @Autowired
    MenuEntityRepository menuEntityRepo;

    @Autowired
    CompanyRepository companyRepo;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public CompanyModel convertCompanyToModel(Company company) {
        CompanyModel companyModel = new CompanyModel();
        companyModel.setId(company.getId());
        companyModel.setCompanyName(company.getCompanyName());
        companyModel.setDisplayName(company.getDisplayName());
        City city = cityRepo.findById(company.getCityId()).get();
        companyModel.setCity(city);
        companyModel.setThumb(company.getThumb());
        companyModel.setUrl(company.getUrl());
        companyModel.setEmail(company.getEmail());
        companyModel.setPhoneOne(company.getPhoneOne());
        companyModel.setPhoneTwo(company.getPhoneTwo());
        companyModel.setPhoneThree(company.getPhoneThree());
        companyModel.setLogo(company.getLogo());
        companyModel.setDelivery(company.getDelivery());
        companyModel.setDeliveryTimeMin(company.getDeliveryTimeMin());
        companyModel.setCommentCount(company.getCommentCount());
        companyModel.setDeliveryCondition(company.getDeliveryCondition());
        companyModel.setPayTypeCash(company.getPayTypeCash());
        companyModel.setPayTypeCard(company.getPayTypeCard());
        companyModel.setPayTypeWallet(company.getPayTypeWallet());
        companyModel.setWeekdayStart(company.getWeekdayStart());
        companyModel.setWeekdayEnd(company.getWeekdayEnd());
        companyModel.setWeekdayWork(company.getWeekdayWork());
        companyModel.setDayoffStart(company.getDayoffStart());
        companyModel.setDayoffEnd(company.getDayoffEnd());
        companyModel.setDayoffWork(company.getDayoffWork());
        companyModel.setFoodPoint(company.getFoodPoint());
        companyModel.setAction(company.getAction());

        return companyModel;
    }

    public Company convertCompanyModelToEntity(CompanyModel model) {
        Company company = new Company();
        Integer companyId = AppConstants.FAKE_ID.equals(model.getId()) ? null : model.getId();
        company.setId(companyId);
        company.setCompanyName(model.getCompanyName().toUpperCase());
        company.setDisplayName(model.getDisplayName());
        company.setCityId(model.getCity().getId());
        company.setThumb(model.getThumb());
        company.setUrl(model.getUrl());
        company.setEmail(model.getEmail());
        company.setPhoneOne(model.getPhoneOne());
        company.setPhoneTwo(model.getPhoneTwo());
        company.setPhoneThree(model.getPhoneThree());
        company.setLogo(model.getLogo());
        company.setDelivery(model.getDelivery());
        company.setDeliveryTimeMin(model.getDeliveryTimeMin());
        company.setCommentCount(model.getCommentCount());
        company.setDeliveryCondition(model.getDeliveryCondition());
        company.setPayTypeCash(model.getPayTypeCash());
        company.setPayTypeCard(model.getPayTypeCard());
        company.setPayTypeWallet(model.getPayTypeWallet());
        company.setWeekdayStart(model.getWeekdayStart());
        company.setWeekdayEnd(model.getWeekdayEnd());
        company.setWeekdayWork(model.getWeekdayWork());
        company.setDayoffStart(model.getDayoffStart());
        company.setDayoffEnd(model.getDayoffEnd());
        company.setDayoffWork(model.getDayoffWork());
        company.setFoodPoint(model.getFoodPoint());
        company.setAction(model.getAction());
        return company;
    }


    public MenuTypeModel convertMenuTypeToModel(MenuType menuType) {
        MenuTypeModel model = new MenuTypeModel();
        model.setId(menuType.getId());
        model.setName(menuType.getName());
        model.setDisplayName(menuType.getDisplayName());
        return model;
    }

    public MenuType convertMenuTypeModelToEntity(MenuTypeModel model) {
        MenuType menuType = new MenuType();
        menuType.setId(model.getId());
        menuType.setName(model.getName());
        menuType.setDisplayName(model.getDisplayName());
        return menuType;
    }

    public MenuCategoryModel convertMenuCategoryToModel(MenuCategory menuCategory, Integer typeId) {
        MenuCategoryModel model = new MenuCategoryModel();
        model.setId(menuCategory.getId());
        model.setMenuTypeId(typeId);
        model.setName(menuCategory.getName());
        model.setDisplayName(menuCategory.getDisplayName());
        return model;
    }

    public MenuCategory convertMenuCategoryModelToEntity(MenuCategoryModel model) {
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setId(model.getId());
        menuCategory.setName(model.getName());
        menuCategory.setDisplayName(model.getDisplayName());
        return menuCategory;
    }

    public MenuEntityModel convertMenuEntityToModel(MenuEntity menuEntity, Integer companyId, Integer typeId,
                                                    Integer categoryId) {
        MenuEntityModel menuEntityModel = new MenuEntityModel();
        menuEntityModel.setId(menuEntity.getId());
        menuEntityModel.setCompanyId(companyId);
        menuEntityModel.setTypeId(typeId);
        menuEntityModel.setCategoryId(categoryId);
        menuEntityModel.setName(menuEntity.getName());
        menuEntityModel.setDisplayName(menuEntity.getDisplayName());
        menuEntityModel.setDescription(menuEntity.getDescription());
        menuEntityModel.setImageUrl(menuEntity.getImageUrl());
        menuEntityModel.setWeightOne(menuEntity.getWeightOne());
        menuEntityModel.setSizeOne(menuEntity.getSizeOne());
        menuEntityModel.setPriceOne(menuEntity.getPriceOne());
        menuEntityModel.setWeightTwo(menuEntity.getWeightTwo());
        menuEntityModel.setSizeTwo(menuEntity.getSizeTwo());
        menuEntityModel.setPriceTwo(menuEntity.getPriceTwo());
        menuEntityModel.setWeightThree(menuEntity.getWeightThree());
        menuEntityModel.setSizeThree(menuEntity.getSizeThree());
        menuEntityModel.setPriceThree(menuEntity.getPriceThree());
        menuEntityModel.setWeightFour(menuEntity.getWeightFour());
        menuEntityModel.setSizeFour(menuEntity.getSizeFour());
        menuEntityModel.setPriceFour(menuEntity.getPriceFour());
        return menuEntityModel;
    }


    public MenuEntityModel convertRawMenuEntityToModel(Object[] obj, Integer categoryId) {

        MenuEntityModel menuEntityModel = new MenuEntityModel();
        menuEntityModel.setCompanyId((Integer) obj[0]);
        menuEntityModel.setTypeId((Integer) obj[1]);
        menuEntityModel.setCategoryId(categoryId);
        menuEntityModel.setId((Integer) obj[2]);
        menuEntityModel.setName((String) obj[3]);
        menuEntityModel.setDisplayName((String) obj[4]);
        menuEntityModel.setDescription((String) obj[5]);
        menuEntityModel.setImageUrl((String) obj[6]);
        menuEntityModel.setWeightOne((String) obj[7]);
        menuEntityModel.setSizeOne((String) obj[8]);
        menuEntityModel.setPriceOne((Integer) obj[9]);
        menuEntityModel.setWeightTwo((String) obj[10]);
        menuEntityModel.setSizeTwo((String) obj[11]);
        menuEntityModel.setPriceTwo((Integer) obj[12]);
        menuEntityModel.setWeightThree((String) obj[13]);
        menuEntityModel.setSizeThree((String) obj[14]);
        menuEntityModel.setPriceThree((Integer) obj[15]);
        menuEntityModel.setWeightFour((String) obj[16]);
        menuEntityModel.setSizeFour((String) obj[17]);
        menuEntityModel.setPriceFour((Integer) obj[18]);
        return menuEntityModel;
    }

    public ParseMenuModel convertParseMenuToModel(ParseMenu parseMenu) {
        ParseMenuModel parseMenuModel = new ParseMenuModel();
        parseMenuModel.setId(parseMenu.getId());
        parseMenuModel.setCompanyId(parseMenu.getCompanyId());
        parseMenuModel.setTypeId(parseMenu.getTypeId());
        parseMenuModel.setCategoryId(parseMenu.getCategoryId());
        parseMenuModel.setParseUrl(parseMenu.getParseUrl());
        parseMenuModel.setParseUrlTwo(parseMenu.getParseUrlTwo());
        parseMenuModel.setParseUrlThree(parseMenu.getParseUrlThree());
        parseMenuModel.setParseUrlFour(parseMenu.getParseUrlFour());
        parseMenuModel.setPrefixUrl(parseMenu.getPrefixUrl());
        parseMenuModel.setTagTrash(parseMenu.getTagTrash());
        parseMenuModel.setTagEndSection(parseMenu.getTagEndSection());
        parseMenuModel.setTagName(parseMenu.getTagName());
        parseMenuModel.setTagDescription(parseMenu.getTagDescription());
        parseMenuModel.setTagImageUrl(parseMenu.getTagImageUrl());
        parseMenuModel.setTagWeightOne(parseMenu.getTagWeightOne());
        parseMenuModel.setTagSizeOne(parseMenu.getTagSizeOne());
        parseMenuModel.setTagPriceOne(parseMenu.getTagPriceOne());
        parseMenuModel.setTagWeightTwo(parseMenu.getTagWeightTwo());
        parseMenuModel.setTagSizeTwo(parseMenu.getTagSizeTwo());
        parseMenuModel.setTagPriceTwo(parseMenu.getTagPriceTwo());
        parseMenuModel.setTagWeightThree(parseMenu.getTagWeightThree());
        parseMenuModel.setTagSizeThree(parseMenu.getTagSizeThree());
        parseMenuModel.setTagPriceThree(parseMenu.getTagPriceThree());
        parseMenuModel.setTagWeightFour(parseMenu.getTagWeightFour());
        parseMenuModel.setTagSizeFour(parseMenu.getTagSizeFour());
        parseMenuModel.setTagPriceFour(parseMenu.getTagPriceFour());
        parseMenuModel.setProcessed(parseMenu.getProcessed());
        parseMenuModel.setLastUpdate(parseMenu.getLastUpdate());
        parseMenuModel.setUpdateResult(parseMenu.getUpdateResult());
        parseMenuModel.setDescription(parseMenu.getDescription());
        return parseMenuModel;
    }

    public void convertParseMenuModelToEntity(ParseMenu parseMenu, ParseMenuModel parseMenuModel) throws Exception {
        parseMenu.setParseUrl(parseMenuModel.getParseUrl());
        parseMenu.setParseUrlTwo(parseMenuModel.getParseUrlTwo());
        parseMenu.setParseUrlThree(parseMenuModel.getParseUrlThree());
        parseMenu.setParseUrlFour(parseMenuModel.getParseUrlFour());
        parseMenu.setPrefixUrl(parseMenuModel.getPrefixUrl());
        parseMenu.setTagTrash(parseMenuModel.getTagTrash());
        parseMenu.setTagEndSection(parseMenuModel.getTagEndSection());
        parseMenu.setTagName(parseMenuModel.getTagName());
        parseMenu.setTagDescription(parseMenuModel.getTagDescription());
        parseMenu.setTagImageUrl(parseMenuModel.getTagImageUrl());
        parseMenu.setTagWeightOne(parseMenuModel.getTagWeightOne());
        parseMenu.setTagSizeOne(parseMenuModel.getTagSizeOne());
        parseMenu.setTagPriceOne(parseMenuModel.getTagPriceOne());
        parseMenu.setTagWeightTwo(parseMenuModel.getTagWeightTwo());
        parseMenu.setTagSizeTwo(parseMenuModel.getTagSizeTwo());
        parseMenu.setTagPriceTwo(parseMenuModel.getTagPriceTwo());
        parseMenu.setTagWeightThree(parseMenuModel.getTagWeightThree());
        parseMenu.setTagSizeThree(parseMenuModel.getTagSizeThree());
        parseMenu.setTagPriceThree(parseMenuModel.getTagPriceThree());
        parseMenu.setTagWeightFour(parseMenuModel.getTagWeightFour());
        parseMenu.setTagSizeFour(parseMenuModel.getTagSizeFour());
        parseMenu.setTagPriceFour(parseMenuModel.getTagPriceFour());
        parseMenu.setProcessed(AppConstants.PROCEED);
        parseMenu.setLastUpdate(parseMenuModel.getLastUpdate());
        parseMenu.setUpdateResult(parseMenuModel.getUpdateResult());
        parseMenu.setDescription(parseMenuModel.getDescription());
    }

    public List<MenuTypeModel> convertMenuTypesToModelList(List<MenuType> menuTypes) {
        List<MenuTypeModel> menuTypeModels = new LinkedList<>();
        for (MenuType menuType : menuTypes) {
            menuTypeModels.add(convertMenuTypeToModel(menuType));
        }
        return menuTypeModels;
    }

    public List<MenuCategoryModel> convertMenuCategoriesToModelList(List<MenuCategory> menuCategories) {
        List<MenuCategoryModel> menuCategoryModels = new LinkedList<>();
        for (MenuCategory menuCategory : menuCategories) {
            menuCategoryModels.add(convertMenuCategoryToModel(menuCategory, null));
        }
        return menuCategoryModels;
    }

    public List<DictionaryModel> convertCitiesToModelList(List<City> cities) {
        List<DictionaryModel> cityModels = new LinkedList<>();
        for (City city : cities) {
            DictionaryModel model = new DictionaryModel();
            model.setId(city.getId());
            model.setName(city.getNameEn());
            model.setDisplayName(city.getName());
            model.setLatitude(city.getLatitude().toString());
            model.setLongitude(city.getLongitude().toString());
            model.setUrl(city.getUrl());
            cityModels.add(model);
        }
        return cityModels;
    }

    public List<CompanyModel> convertCompaniesToModelList(List<Company> companies) {
        List<CompanyModel> companyModels = new LinkedList<>();
        for (Company company : companies) {
            companyModels.add(convertCompanyToModel(company));
        }
        return companyModels;
    }

    public OurClient convertModelToOurClient(OurClientModel ourClientModel) {
        OurClient ourClient = new OurClient();
        if (AppConstants.FAKE_ID.equals(ourClientModel.getId())) {
            ourClient.setId(null);
        }
        ourClient.setNickName( ourClientModel.getNickName() );
        ourClient.setPhoto( ourClientModel.getPhoto() );
        ourClient.setEmail(ourClientModel.getEmail() != null ? ourClientModel.getEmail().toLowerCase().trim() : null);
        ourClient.setPhone(ourClientModel.getPhone() != null ? ourClientModel.getPhone().trim() : null);
        ourClient.setPassword(bcryptEncoder.encode(ourClientModel.getPassword()));
        ourClient.setUuid(UUID.randomUUID().toString());
        ourClient.setPayType( ourClientModel.getPayType() );
        ourClient.setBonuses( convertBonusModelCollectionToEntities( ourClientModel.getBonusModels() ) );
        if ( ourClientModel.getClientLocationModel() != null ){
            ourClient.setCity( ourClientModel.getClientLocationModel().getCity() );
            ourClient.setStreet( ourClientModel.getClientLocationModel().getStreet() );
            ourClient.setHouse( ourClientModel.getClientLocationModel().getHouse() );
            ourClient.setEntrance( ourClientModel.getClientLocationModel().getEntrance() );
            ourClient.setFloor( ourClientModel.getClientLocationModel().getFloor() );
            ourClient.setIntercom( ourClientModel.getClientLocationModel().getIntercom() );
            ourClient.setLatitude( ourClientModel.getClientLocationModel().getLatitude() );
            ourClient.setLongitude( ourClientModel.getClientLocationModel().getLongitude() );
        }
        return ourClient;
    }
    
    public void updateClientLocation(OurClient ourClient, ClientLocationModel clientLocationModel){
        ourClient.setCity( clientLocationModel.getCity() );
        ourClient.setStreet( clientLocationModel.getStreet() );
        ourClient.setHouse( clientLocationModel.getHouse() );
        ourClient.setEntrance( clientLocationModel.getEntrance() );
        ourClient.setFloor( clientLocationModel.getFloor() );
        ourClient.setIntercom( clientLocationModel.getIntercom() );
        ourClient.setLatitude( clientLocationModel.getLatitude() );
        ourClient.setLongitude( clientLocationModel.getLongitude() );
    }

    public  String getEncondedPasword(String rawPassword){
        return bcryptEncoder.encode( rawPassword );
    }

    public OurClientModel convertOurClientToModel(OurClient ourClient, List<FavoriteCompany> favoriteCompanies) {
        OurClientModel ourClientModel = new OurClientModel();
        ClientLocationModel clientLocationModel = new ClientLocationModel();
        ourClientModel.setNickName( ourClient.getNickName() );
        ourClientModel.setPhoto( ourClient.getPhoto() );
        ourClientModel.setId(ourClient.getId());
        ourClientModel.setEmail(ourClient.getEmail());
        ourClientModel.setPhone(ourClient.getPhone());
        ourClientModel.setPassword( ourClient.getPassword() );
        ourClientModel.setUuid(ourClient.getUuid());
        ourClientModel.setPayType( ourClient.getPayType() );
        clientLocationModel.setCity( ourClient.getCity() );
        clientLocationModel.setUuid( ourClient.getUuid() );
        clientLocationModel.setStreet( ourClient.getStreet() );
        clientLocationModel.setHouse( ourClient.getHouse() );
        clientLocationModel.setEntrance( ourClient.getEntrance() );
        clientLocationModel.setFloor( ourClient.getFloor() );
        clientLocationModel.setIntercom( ourClient.getIntercom() );
        clientLocationModel.setLatitude( ourClient.getLatitude() );
        clientLocationModel.setLongitude( ourClient.getLongitude() );
        ourClientModel.setClientLocationModel(clientLocationModel);
        if (favoriteCompanies != null) {
            List<FavoriteCompanyModel> favoriteCompanyModels = new LinkedList<>();
            for (FavoriteCompany favorite : favoriteCompanies) {
                favoriteCompanyModels.add( convertFavoriteCompanyToModel( favorite ) );
            }
            ourClientModel.setFavoriteCompanies(favoriteCompanyModels);
        }
        ourClientModel.setBonusModels( convertBonusCollectionToModels( ourClient.getBonuses() ) );
        return ourClientModel;
    }

    public FavoriteCompany convertModelToFavoriteCompany(FavoriteCompanyModel model ) {
        FavoriteCompany favoriteCompany = new FavoriteCompany();
        favoriteCompany.setId( model.getId() );
        favoriteCompany.setClientId( model.getClientId() );
        favoriteCompany.setCompanyId( model.getCompanyId() );
        return favoriteCompany;
    }

    public FavoriteCompanyModel convertFavoriteCompanyToModel(FavoriteCompany favoriteCompany ) {
        FavoriteCompanyModel model = new FavoriteCompanyModel();
        model.setId( favoriteCompany.getId() );
        model.setClientId( favoriteCompany.getClientId() );
        model.setCompanyId( favoriteCompany.getCompanyId() );
        return model;
    }

    public ClientOrder convertModelToClientOrder(ClientOrderModel clientOrderModel) {
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setId(AppConstants.FAKE_ID.equals(clientOrderModel.getId()) ? null : clientOrderModel.getId());
        clientOrder.setClientUuid( clientOrderModel.getClientUuid() );
        clientOrder.setOrderDate( clientOrderModel.getOrderDate() );
        clientOrder.setOrderTime(clientOrderModel.getOrderTime() );
        clientOrder.setOrderPrice(clientOrderModel.getOrderPrice() );
        clientOrder.setOrderStatus(clientOrderModel.getOrderStatus() );
        clientOrder.setNickName(clientOrderModel.getNickName());
        clientOrder.setEmail(clientOrderModel.getEmail());
        clientOrder.setPhone(clientOrderModel.getPhone());
        clientOrder.setCity(clientOrderModel.getCity());
        clientOrder.setStreet(clientOrderModel.getStreet());
        clientOrder.setBuilding(clientOrderModel.getBuilding());
        clientOrder.setEntry(clientOrderModel.getEntry());
        clientOrder.setFloor(clientOrderModel.getFloor());
        clientOrder.setFlat(clientOrderModel.getFlat());
        clientOrder.setIntercom(clientOrderModel.getIntercom());
        clientOrder.setNeedChange(clientOrderModel.getNeedChange());
        clientOrder.setComment(clientOrderModel.getComment());
        clientOrder.setPayType(clientOrderModel.getPayType());
        return clientOrder;
    }

    public List<BasketModel> convertOrderEntitiesToBasketModel(List<OrderEntity> orderEntities){
        List<BasketModel> basketModels = new ArrayList<>();
        for(OrderEntity orderEntity : orderEntities ){
            MenuEntity menuEntity = menuEntityRepo.findById( orderEntity.getEntityId() ).get();
            MenuEntityModel entityModel = convertMenuEntityToModel( menuEntity, orderEntity.getCompanyId(), null, null );
            entityModel.setWspType( orderEntity.getWspType() );
            entityModel.setCount( orderEntity.getCount() );
            CompanyModel companyModel = convertCompanyToModel( companyRepo.findById( orderEntity.getCompanyId() ).get() );
            Optional<BasketModel> optional = basketModels.stream().filter( basket -> basket.getCompany().getId().equals( companyModel.getId() ) ).findFirst();
            if ( optional.isPresent() ){
                optional.get().getBasket().add( entityModel );
            } else {
                BasketModel basketModel = new BasketModel();
                basketModel.setCompany( companyModel );
                basketModel.setBasket( new ArrayList<>() );
                basketModel.getBasket().add( entityModel );
                basketModels.add( basketModel );
            }
        }
        return basketModels;
    }



    public ClientOrderModel convertClientOrderToModel(ClientOrder clientOrder  ) {
        ClientOrderModel clientOrderModel = new ClientOrderModel();
        clientOrderModel.setId( clientOrder.getId());
        clientOrderModel.setClientUuid( clientOrder.getClientUuid() );
        clientOrderModel.setCompanyOneId( clientOrder.getCompanyOneId() );
        clientOrderModel.setCompanyOneName( clientOrder.getCompanyOneName() );
        clientOrderModel.setCompanyTwoId( clientOrder.getCompanyTwoId() );
        clientOrderModel.setCompanyTwoName( clientOrder.getCompanyTwoName() );
        clientOrderModel.setCompanyLogo( clientOrder.getCompanyLogo() );
        clientOrderModel.setOrderDate( clientOrder.getOrderDate() );
        clientOrderModel.setOrderTime(clientOrder.getOrderTime() );
        clientOrderModel.setOrderPrice(clientOrder.getOrderPrice() );
        clientOrderModel.setOrderStatus(clientOrder.getOrderStatus() );
        clientOrderModel.setNickName(clientOrder.getNickName());
        clientOrderModel.setEmail(clientOrder.getEmail());
        clientOrderModel.setPhone(clientOrder.getPhone());
        clientOrderModel.setCity(clientOrder.getCity());
        clientOrderModel.setStreet(clientOrder.getStreet());
        clientOrderModel.setBuilding(clientOrder.getBuilding());
        clientOrderModel.setEntry(clientOrder.getEntry());
        clientOrderModel.setFloor(clientOrder.getFloor());
        clientOrderModel.setFlat(clientOrder.getFlat());
        clientOrderModel.setIntercom(clientOrder.getIntercom());
        clientOrderModel.setNeedChange(clientOrder.getNeedChange());
        clientOrderModel.setComment(clientOrder.getComment());
        clientOrderModel.setPayType(clientOrder.getPayType());
        return clientOrderModel;
    }

    public List<OrderEntity> convertModelsToOrderEntityList(Integer orderId, List<BasketModel> basketModels) {
        List<OrderEntity> orderEntities = new LinkedList<>();
        for (BasketModel basketModel : basketModels) {
            for (MenuEntityModel entityModel : basketModel.getBasket()) {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setId(null);
                orderEntity.setOrderId(orderId);
                orderEntity.setCompanyId(basketModel.getCompany().getId());
                orderEntity.setEntityId( entityModel.getId());
                orderEntity.setCount(entityModel.getCount());
                orderEntity.setWspType(entityModel.getWspType());
                switch (entityModel.getWspType().toUpperCase()) {
                    case AppConstants.WSP_TYPE_ONE:
                        orderEntity.setPrice(entityModel.getPriceOne() * entityModel.getCount());
                        break;
                    case AppConstants.WSP_TYPE_TWO:
                        orderEntity.setPrice(entityModel.getPriceTwo() * entityModel.getCount());
                        break;
                    case AppConstants.WSP_TYPE_THREE:
                        orderEntity.setPrice(entityModel.getPriceThree() * entityModel.getCount());
                        break;
                    case AppConstants.WSP_TYPE_FOUR:
                        orderEntity.setPrice(entityModel.getPriceFour() * entityModel.getCount());
                        break;
                }
                orderEntities.add(orderEntity);
            }
        }
        return orderEntities;
    }

    public List<CompanyActionModel> convertCompanyActionsToModels(List<CompanyAction> companyActions) {
        List<CompanyActionModel> companyActionModels = new LinkedList<>();
        for (CompanyAction companyAction : companyActions) {
            companyActionModels.add(new CompanyActionModel(companyAction.getCompanyName(), companyAction.getActionImgUrl()));
        }
        return companyActionModels;
    }

    public BonusModel convertBonusToModel( Bonus bonus ){
        BonusModel bonusModel = new BonusModel();
        bonusModel.setId( bonus.getId() );
        bonusModel.setUuid( bonus.getClientUuid() );
        bonusModel.setCompanyId( bonus.getCompanyId() );
        bonusModel.setCompanyName( bonus.getCompanyName() );
        bonusModel.setCompanyLogo( bonus.getCompanyLogo() );
        bonusModel.setBonusType( bonus.getBonusType() );
        bonusModel.setBonusValue( bonus.getBonusValue() );
        return bonusModel;
    }
    
    public Bonus convertBonusModelToEntity( BonusModel bonusModel ){
        Bonus bonus = new Bonus();
        bonus.setId( bonusModel.getId() );
        bonus.setClientUuid( bonusModel.getUuid() );
        bonus.setCompanyId( bonusModel.getCompanyId() );
        bonus.setCompanyName( bonusModel.getCompanyName() );
        bonus.setCompanyLogo( bonusModel.getCompanyLogo() );
        bonus.setBonusType( bonusModel.getBonusType() );
        bonus.setBonusValue( bonusModel.getBonusValue() );
        return bonus;
    }


    public List<BonusModel> convertBonusCollectionToModels(List<Bonus> bonuses ){
        List<BonusModel> bonusModels =  new ArrayList<>();
        if( bonuses != null ){
            for( Bonus bonus: bonuses ){
                bonusModels.add( convertBonusToModel( bonus ) );
            }
        }
        return bonusModels;
    }

    public List<Bonus> convertBonusModelCollectionToEntities(List<BonusModel> bonusModels ){
        List<Bonus> bonuses =  new ArrayList<>();
        if( bonusModels != null ){
            for( BonusModel bonusModel: bonusModels ){
                bonuses.add( convertBonusModelToEntity( bonusModel ) );
            }
        }
        return bonuses;
    }


    public boolean isPasswordMatches(String rawPswd, String encodePswd) {
        return bcryptEncoder.matches(rawPswd, encodePswd);
    }

}
