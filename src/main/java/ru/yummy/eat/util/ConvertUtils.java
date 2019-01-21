package ru.yummy.eat.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.*;
import ru.yummy.eat.model.*;
import ru.yummy.eat.repo.CityRepository;

import java.util.LinkedList;
import java.util.List;

@Component
public class ConvertUtils {

    @Autowired
    CityRepository cityRepo;

    public CompanyModel convertCompanyToModel(Company company){
        CompanyModel companyModel = new CompanyModel();
        companyModel.setId( company.getId() );
        companyModel.setCompanyName( company.getCompanyName() );
        companyModel.setDisplayName( company.getDisplayName() );
        City city = cityRepo.findById( company.getCityId() ).get();
        companyModel.setCity( city );
        companyModel.setUrl( company.getUrl() );
        companyModel.setEmail( company.getEmail() );
        companyModel.setPhoneOne( company.getPhoneOne() );
        companyModel.setPhoneTwo( company.getPhoneTwo() );
        companyModel.setPhoneThree( company.getPhoneThree() );
        companyModel.setLogo( company.getLogo() );
        companyModel.setDelivery( company.getDelivery() );
        companyModel.setCommentCount( company.getCommentCount() );
        companyModel.setDeliveryCondition( company.getDeliveryCondition() );
        companyModel.setPayTypeCash( company.getPayTypeCash() );
        companyModel.setPayTypeCard( company.getPayTypeCard() );
        companyModel.setPayTypeWallet( company.getPayTypeWallet() );
        companyModel.setWeekdayStart( company.getWeekdayStart() );
        companyModel.setWeekdayEnd( company.getWeekdayEnd() );
        companyModel.setWeekdayWork( company.getWeekdayWork() );
        companyModel.setDayoffStart( company.getDayoffStart() );
        companyModel.setDayoffEnd( company.getDayoffEnd() );
        companyModel.setDayoffWork( company.getDayoffWork() );
        companyModel.setFoodPoint( company.getFoodPoint() );
        companyModel.setAction( company.getAction() );

        return companyModel;
    }

    public Company convertCompanyModelToEntity(CompanyModel model){
        Company company = new Company();
        Integer companyId = AppConstants.FAKE_ID.equals( model.getId() ) ? null : model.getId();
        company.setId( companyId );
        company.setCompanyName( model.getCompanyName().toUpperCase() );
        company.setDisplayName( model.getDisplayName() );
        company.setCityId( model.getCity().getId() );
        company.setUrl( model.getUrl() );
        company.setEmail( model.getEmail() );
        company.setPhoneOne( model.getPhoneOne() );
        company.setPhoneTwo( model.getPhoneTwo() );
        company.setPhoneThree( model.getPhoneThree() );
        company.setLogo( model.getLogo() );
        company.setDelivery( model.getDelivery() );
        company.setCommentCount( model.getCommentCount() );
        company.setDeliveryCondition( model.getDeliveryCondition() );
        company.setPayTypeCash( model.getPayTypeCash() );
        company.setPayTypeCard( model.getPayTypeCard() );
        company.setPayTypeWallet( model.getPayTypeWallet() );
        company.setWeekdayStart( model.getWeekdayStart() );
        company.setWeekdayEnd( model.getWeekdayEnd() );
        company.setWeekdayWork( model.getWeekdayWork() );
        company.setDayoffStart( model.getDayoffStart() );
        company.setDayoffEnd( model.getDayoffEnd() );
        company.setDayoffWork( model.getDayoffWork() );
        company.setFoodPoint( model.getFoodPoint() );
        company.setAction( model.getAction() );
        return company;
    }


    public MenuTypeModel convertMenuTypeToModel(MenuType menuType){
        MenuTypeModel model = new MenuTypeModel();
        model.setId( menuType.getId() );
        model.setName( menuType.getName() );
        model.setDisplayName( menuType.getDisplayName() );
        return model;
    }

    public MenuType convertMenuTypeModelToEntity(MenuTypeModel model){
        MenuType menuType = new MenuType();
        menuType.setId( model.getId() );
        menuType.setName( model.getName() );
        menuType.setDisplayName( model.getDisplayName() );
        return menuType;
    }

    public MenuCategoryModel convertMenuCategoryToModel(MenuCategory menuCategory, Integer typeId){
        MenuCategoryModel model = new MenuCategoryModel();
        model.setId( menuCategory.getId() );
        model.setMenuTypeId( typeId );
        model.setName( menuCategory.getName() );
        model.setDisplayName( menuCategory.getDisplayName() );
        return model;
    }

    public MenuCategory convertMenuCategoryModelToEntity(MenuCategoryModel model){
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setId( model.getId() );
        menuCategory.setName( model.getName() );
        menuCategory.setDisplayName( model.getDisplayName() );
        return menuCategory;
    }

    public MenuEntityModel convertMenuEntityToModel( MenuEntity menuEntity, Integer companyId, Integer typeId,
                                                            Integer categoryId){
        MenuEntityModel menuEntityModel = new MenuEntityModel();
        menuEntityModel.setId( menuEntity.getId() );
        menuEntityModel.setCompanyId( companyId );
        menuEntityModel.setTypeId( typeId );
        menuEntityModel.setCategoryId( categoryId );
        menuEntityModel.setName( menuEntity.getName() );
        menuEntityModel.setDisplayName( menuEntity.getDisplayName() );
        menuEntityModel.setDescription( menuEntity.getDescription() );
        menuEntityModel.setImageUrl( menuEntity.getImageUrl() );
        menuEntityModel.setWeightOne( menuEntity.getWeightOne() );
        menuEntityModel.setSizeOne( menuEntity.getSizeOne() );
        menuEntityModel.setPriceOne( menuEntity.getPriceOne() );
        menuEntityModel.setWeightTwo( menuEntity.getWeightTwo() );
        menuEntityModel.setSizeTwo( menuEntity.getSizeTwo() );
        menuEntityModel.setPriceTwo( menuEntity.getPriceTwo() );
        menuEntityModel.setWeightThree( menuEntity.getWeightThree() );
        menuEntityModel.setSizeThree( menuEntity.getSizeThree() );
        menuEntityModel.setPriceThree( menuEntity.getPriceThree() );
        menuEntityModel.setWeightFour( menuEntity.getWeightFour() );
        menuEntityModel.setSizeFour( menuEntity.getSizeFour() );
        menuEntityModel.setPriceFour( menuEntity.getPriceFour() );
        menuEntityModel.setStatus( menuEntity.getStatus() );
        return menuEntityModel;
    }

    public ParseMenuModel convertParseMenuToModel( ParseMenu parseMenu ){
        ParseMenuModel parseMenuModel = new ParseMenuModel();
        parseMenuModel.setId(parseMenu.getId());
        parseMenuModel.setCompanyId( parseMenu.getCompanyId() );
        parseMenuModel.setTypeId( parseMenu.getTypeId() );
        parseMenuModel.setCategoryId( parseMenu.getCategoryId() );
        parseMenuModel.setParseUrl(parseMenu.getParseUrl());
        parseMenuModel.setPrefixUrl( parseMenu.getPrefixUrl() );
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
        return parseMenuModel;
    }

    public void convertParseMenuModelToEntity( ParseMenu parseMenu, ParseMenuModel parseMenuModel ) throws Exception{
        parseMenu.setParseUrl(parseMenuModel.getParseUrl());
        parseMenu.setPrefixUrl( parseMenuModel.getPrefixUrl() );
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
        parseMenu.setProcessed( AppConstants.PROCEED );
    }

    public List<MenuTypeModel> convertMenuTypesToModelList( List<MenuType> menuTypes ){
        List<MenuTypeModel> menuTypeModels = new LinkedList<>();
        for(MenuType menuType: menuTypes ){
            menuTypeModels.add( convertMenuTypeToModel( menuType ) );
        }
        return menuTypeModels;
    }

    public List<MenuCategoryModel> convertMenuCategoriesToModelList( List<MenuCategory> menuCategories ){
        List<MenuCategoryModel> menuCategoryModels = new LinkedList<>();
        for(MenuCategory menuCategory: menuCategories ){
            menuCategoryModels.add( convertMenuCategoryToModel( menuCategory,null ) );
        }
        return menuCategoryModels;
    }

    public List<DictionaryModel> convertCitiesToModelList( List<City> cities ){
        List<DictionaryModel> cityModels = new LinkedList<>();
        for(City city: cities ){
            DictionaryModel model =  new DictionaryModel();
            model.setId( city.getId() );
            model.setName( city.getNameEn() );
            model.setDisplayName( city.getName() );
            cityModels.add( model );
        }
        return cityModels;
    }

    public List<CompanyModel> convertCompaniesToModelList( List<Company> companies ){
        List<CompanyModel> companyModels = new LinkedList<>();
        for(Company company: companies ){
            companyModels.add( convertCompanyToModel( company ) );
        }
        return companyModels;
    }


}