package ru.yummy.food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.food.entity.Company;
import ru.yummy.food.entity.MenuCategory;
import ru.yummy.food.entity.MenuType;
import ru.yummy.food.model.*;
import ru.yummy.food.repo.CompanyRepository;
import ru.yummy.food.repo.MenuCategoryRepository;
import ru.yummy.food.repo.MenuTypeRepository;
import ru.yummy.food.util.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

@Service("companyService")
public class CompanyServiceImpl {

    @Autowired
    CompanyRepository companyRepo;

    @Autowired
    MenuTypeRepository menuTypeRepo;

    @Autowired
    MenuCategoryRepository menuCategoryRepo;

    @Autowired
    ConvertUtils convertUtils;

    public List<CompanyModel> getAllCompanies(){
        List<Company> companies = (List<Company>) companyRepo.findAll();
        List<CompanyModel> models = new ArrayList<>();
        for( Company company: companies ){
            models.add( convertUtils.convertCompanyToModel( company ) );
        }
        return models;
    }

    public CompanyInfo getCompanyInfo(Integer companyId){
        CompanyInfo companyInfo = new CompanyInfo();
        CompanyModel company  = convertUtils.convertCompanyToModel( companyRepo.findById( companyId ).get() );
        List<MenuType> menuTypes = menuTypeRepo.findTypesByCompanyId( Integer.valueOf( companyId ) );
        List<MenuTypeModel> menuTypeModels = new ArrayList<>();
        for(MenuType menuType : menuTypes ){
            MenuTypeModel menuTypeModel = convertUtils.convertMenuTypeToModel( menuType );
            List<MenuCategoryModel> menuCategoryModels = new ArrayList<>();
            List<MenuCategory> categories = menuCategoryRepo.findCategoriesByCompanyAndTypeId( Integer.valueOf( companyId ),
                                                menuType.getId() );
            for(MenuCategory category: categories ){
                MenuCategoryModel menuCategoryModel = convertUtils.convertMenuCategoryToModel( category );
                menuCategoryModel.setMenuTypeId( menuType.getId() );
                menuCategoryModels.add( menuCategoryModel );
            }
            menuTypeModel.setMenuCategories( menuCategoryModels );
            menuTypeModels.add( menuTypeModel );
        }
        companyInfo.setCompanyModel( company );
        companyInfo.setMenuTypes( menuTypeModels );
        return companyInfo;
    }

    public CompanyMenu getCompanyMenu(int companyId, int typeId,int categoryId ){
        CompanyMenu companyMenu =  new CompanyMenu();
        return companyMenu;
    }

}
