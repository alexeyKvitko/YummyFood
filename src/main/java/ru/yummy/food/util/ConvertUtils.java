package ru.yummy.food.util;

import org.pojomatic.annotations.AutoProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yummy.food.entity.*;
import ru.yummy.food.model.*;
import ru.yummy.food.repo.CityRepository;

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
        companyModel.setPhoneOne( company.getPhoneOne() );
        companyModel.setPhoneTwo( company.getPhoneTwo() );
        companyModel.setPhoneThree( company.getPhoneThree() );
        companyModel.setLogo( company.getLogo() );
        return companyModel;
    }


    public MenuTypeModel convertMenuTypeToModel(MenuType menuType){
        MenuTypeModel model = new MenuTypeModel();
        model.setId( menuType.getId() );
        model.setName( menuType.getName() );
        model.setDisplayName( menuType.getDisplayName() );
        return model;
    }

    public MenuCategoryModel convertMenuCategoryToModel(MenuCategory menuCategory, Integer typeId){
        MenuCategoryModel model = new MenuCategoryModel();
        model.setId( menuCategory.getId() );
        model.setMenuTypeId( typeId );
        model.setName( menuCategory.getName() );
        model.setDisplayName( menuCategory.getDisplayName() );
        return model;
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

    public ParseMenuModel convertParseMenuToModel(ParseMenu parseMenu ){
        ParseMenuModel parseMenuModel = new ParseMenuModel();
        parseMenuModel.setId(parseMenu.getId());
        parseMenuModel.setCompanyId( parseMenu.getCompanyId() );
        parseMenuModel.setTypeId( parseMenu.getTypeId() );
        parseMenuModel.setCategoryId( parseMenu.getCategoryId() );
        parseMenuModel.setParseUrl(parseMenu.getParseUrl());
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
}
