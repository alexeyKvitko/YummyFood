package ru.yummy.food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.food.AppConstants;
import ru.yummy.food.entity.*;
import ru.yummy.food.model.*;
import ru.yummy.food.repo.*;
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
    MenuEntityRepository menuEntityRepo;

    @Autowired
    ParseMenuRepository parseRepo;

    @Autowired
    CityRepository cityRepo;

    @Autowired
    ConvertUtils convertUtils;

    public List<CompanyModel> getAllCompanies() {
        List<Company> companies = (List<Company>) companyRepo.findAll();
        List<CompanyModel> models = new ArrayList<>();
        for (Company company : companies) {
            models.add(convertUtils.convertCompanyToModel(company));
        }
        return models;
    }

    public CompanyInfo getCompanyInfo(Integer companyId) {
        CompanyInfo companyInfo = new CompanyInfo();
        CompanyModel company = convertUtils.convertCompanyToModel(companyRepo.findById(companyId).get());
        List<MenuType> menuTypes = menuTypeRepo.findTypesByCompanyId(Integer.valueOf(companyId));
        List<MenuTypeModel> menuTypeModels = new ArrayList<>();
        for (MenuType menuType : menuTypes) {
            MenuTypeModel menuTypeModel = convertUtils.convertMenuTypeToModel(menuType);
            List<MenuCategoryModel> menuCategoryModels = new ArrayList<>();
            List<MenuCategory> categories = menuCategoryRepo.findCategoriesByCompanyAndTypeId(Integer.valueOf(companyId),
                    menuType.getId());
            for (MenuCategory category : categories) {
                menuCategoryModels.add(convertUtils.convertMenuCategoryToModel(category, menuType.getId()));
            }
            menuTypeModel.setMenuCategories(menuCategoryModels);
            menuTypeModels.add(menuTypeModel);
        }
        companyInfo.setCompanyModel(company);
        companyInfo.setMenuTypes(menuTypeModels);
        return companyInfo;
    }

    public CompanyEdit getCompanyEdit(Integer companyId) {
        CompanyEdit companyEdit =  new CompanyEdit();
        CompanyInfo companyInfo = getCompanyInfo( companyId );
        companyEdit.setCompanyModel( companyInfo.getCompanyModel() );
        companyEdit.setMenuTypes( companyInfo.getMenuTypes() );
        companyEdit.setDeliveryMenuTypes( convertUtils.convertMenuTypesToModelList( (List<MenuType>) menuTypeRepo.findAll() ) );
        companyEdit.setDeliveryMenuCategories( convertUtils.convertMenuCategoriesToModelList( (List<MenuCategory>) menuCategoryRepo.findAll() ) );
        companyEdit.setCities( convertUtils.convertCitiesToModelList( cityRepo.findAllByRegionId( AppConstants.CRIMEA_REGION ) ) );
        return companyEdit;
    }

    public CompanyMenu getCompanyMenu(int companyId, int typeId, int categoryId) {
        CompanyMenu companyMenu = new CompanyMenu();
        List<MenuEntity> menuEntities = menuEntityRepo.findMenuEntity(companyId, typeId, categoryId);
        List<MenuEntityModel> entityModels = new ArrayList<>();
        for (MenuEntity menuEntity : menuEntities) {
            if (AppConstants.FAKE_ID.equals(menuEntity.getId())) {
                continue;
            }
            entityModels.add(convertUtils.convertMenuEntityToModel(menuEntity, companyId, typeId, categoryId));
        }
        ParseMenu parseMenu = parseRepo.findParseMenuByCompanyIdAndTypeIdAndCategoryId(companyId, typeId, categoryId);
        companyMenu.setParseMenu(convertUtils.convertParseMenuToModel(parseMenu));
        companyMenu.setMenuEntities(entityModels);
        return companyMenu;
    }

}
