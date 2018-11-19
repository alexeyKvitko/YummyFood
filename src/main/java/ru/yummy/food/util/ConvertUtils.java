package ru.yummy.food.util;

import org.pojomatic.annotations.AutoProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yummy.food.entity.City;
import ru.yummy.food.entity.Company;
import ru.yummy.food.entity.MenuCategory;
import ru.yummy.food.entity.MenuType;
import ru.yummy.food.model.CompanyModel;
import ru.yummy.food.model.MenuCategoryModel;
import ru.yummy.food.model.MenuTypeModel;
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

    public MenuCategoryModel convertMenuCategoryToModel(MenuCategory menuCategory){
        MenuCategoryModel model = new MenuCategoryModel();
        model.setId( menuCategory.getId() );
        model.setName( menuCategory.getName() );
        model.setDisplayName( menuCategory.getDisplayName() );
        return model;
    }
}
