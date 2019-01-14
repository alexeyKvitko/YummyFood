package ru.yummy.food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.food.AppConstants;
import ru.yummy.food.entity.*;
import ru.yummy.food.exception.BusinessLogicException;
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
    CompanyShortRepository companyShortRepo;


    @Autowired
    MenuTypeRepository menuTypeRepo;

    @Autowired
    MenuCategoryRepository menuCategoryRepo;

    @Autowired
    MenuEntityRepository menuEntityRepo;

    @Autowired
    MenuItemRepository menuItemRepo;

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
        CompanyModel company = null;
        List<MenuTypeModel> menuTypeModels = new ArrayList<>();
        List<MenuEntityModel> menuEntityModels = new ArrayList<>();
        if ( AppConstants.FAKE_ID.equals( companyId ) ){
            company = new CompanyModel();
            company.setCity( cityRepo.findById( AppConstants.SIMFEROPOL_ID ).get() );
            companyInfo.setCompanyModel(company);
            companyInfo.setMenuTypes(menuTypeModels);
            return companyInfo;
        }
        company = convertUtils.convertCompanyToModel(companyRepo.findById(companyId).get());
        List<MenuType> menuTypes = menuTypeRepo.findTypesByCompanyId(Integer.valueOf(companyId));
        for (MenuType menuType : menuTypes) {
            MenuTypeModel menuTypeModel = convertUtils.convertMenuTypeToModel(menuType);
            List<MenuCategoryModel> menuCategoryModels = new ArrayList<>();
            List<MenuCategory> categories = menuCategoryRepo.findCategoriesByCompanyAndTypeId(Integer.valueOf(companyId),
                    menuType.getId());
            for (MenuCategory category : categories) {
                menuCategoryModels.add(convertUtils.convertMenuCategoryToModel(category, menuType.getId()));
                menuEntityModels.addAll( getCompanyMenuModels( companyId, menuType.getId(), category.getId()) );
            }
            menuTypeModel.setMenuCategories(menuCategoryModels);
            menuTypeModels.add(menuTypeModel);
        }
        companyInfo.setCompanyModel(company);
        companyInfo.setMenuTypes(menuTypeModels);
        companyInfo.setMenuEntities( menuEntityModels);
        return companyInfo;
    }

    public CompanyEdit getCompanyEdit(Integer companyId) {
        CompanyEdit companyEdit =  new CompanyEdit();
        CompanyInfo companyInfo = getCompanyInfo( companyId );
        CompanyShort companyShort = companyShortRepo.getCompanyShortByCompanyId( companyId );
        companyEdit.setCompanyShort( companyShort != null ? companyShort : new CompanyShort() );
        companyEdit.setCompanyModel( companyInfo.getCompanyModel() );
        companyEdit.setMenuTypes( companyInfo.getMenuTypes() );
        companyEdit.setDeliveryMenuTypes( convertUtils.convertMenuTypesToModelList( (List<MenuType>) menuTypeRepo.findAll() ) );
        companyEdit.setDeliveryMenuCategories( convertUtils.convertMenuCategoriesToModelList( (List<MenuCategory>) menuCategoryRepo.findAll() ) );
        companyEdit.setCities( convertUtils.convertCitiesToModelList( cityRepo.findAllByRegionIdOrderByName( AppConstants.CRIMEA_REGION ) ) );
        return companyEdit;
    }

    private List<MenuEntityModel> getCompanyMenuModels(int companyId, int typeId, int categoryId) {
        List<MenuEntity> menuEntities = menuEntityRepo.findMenuEntity(companyId, typeId, categoryId);
        List<MenuEntityModel> entityModels = new ArrayList<>();
        for (MenuEntity menuEntity : menuEntities) {
            if (AppConstants.FAKE_ID.equals(menuEntity.getId())) {
                continue;
            }
            entityModels.add(convertUtils.convertMenuEntityToModel(menuEntity, companyId, typeId, categoryId));
        }
        return entityModels;
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

    public void addCompanyMenu(int companyId, int typeId, int categoryId) throws BusinessLogicException {
        ParseMenu parseMenu = parseRepo.findParseMenuByCompanyIdAndTypeIdAndCategoryId( companyId, typeId, categoryId );
        if ( parseMenu != null ){
            throw new BusinessLogicException( "Меню уже существует, добавление невозможно !" );
        }
        parseMenu = new ParseMenu();
        parseMenu.setCompanyId( companyId );
        parseMenu.setTypeId( typeId );
        parseMenu.setCategoryId( categoryId );
        MenuItem menuItem = new MenuItem();
        menuItem.setCompanyId( companyId );
        menuItem.setTypeId( typeId );
        menuItem.setCategoryId( categoryId );
        menuItem.setEntityId( AppConstants.FAKE_ID );
        try {
            parseRepo.save( parseMenu );
            menuItemRepo.save( menuItem );
        } catch (Exception e){
            throw new BusinessLogicException( "Добавление невозможно, "+e.getMessage() );
        }
    }

    public void deleteCompanyMenu(int companyId, int typeId, int categoryId) throws BusinessLogicException {
        List<MenuItem> menuItems = menuItemRepo.findAllByCompanyIdAndTypeIdAndCategoryId( companyId, typeId, categoryId );
        if ( (menuItems != null && menuItems.size() > 1) ||
                ( menuItems != null && menuItems.size() == 1 && !AppConstants.FAKE_ID.equals( menuItems.get(0).getEntityId() ) ) ){
            throw new BusinessLogicException( "Удаление невозможно, найдено: "+ menuItems.size()+" блюд");
        }
        ParseMenu parseMenu = parseRepo.findParseMenuByCompanyIdAndTypeIdAndCategoryId( companyId, typeId, categoryId );
        try {
            parseRepo.delete( parseMenu );
            menuItemRepo.delete( menuItems.get(0) );
        } catch (Exception e){
            throw new BusinessLogicException( "Ошибка при попытке удаления, "+e.getMessage() );
        }
    }

    public CompanyEdit saveCompany(CompanyModel companyModel, CompanyShort companyShort) throws BusinessLogicException {
        CompanyEdit companyEdit = null;
        Company company = convertUtils.convertCompanyModelToEntiry( companyModel ); 
        try {
            companyRepo.save( company );
            companyShort.setCompanyId( company.getId() );
            companyShort.setCityId( company.getCityId() );
            companyShort.setCompanyLogo( company.getLogo() );
            companyShort.setCompanyName( company.getDisplayName() );
            companyShortRepo.save( companyShort );
            if ( company.getId() != null ){
              companyEdit = getCompanyEdit( company.getId() );
            }
        } catch (Exception e){
            throw new BusinessLogicException( "Ошибка при попытке записи компании, "+e.getMessage() );
        }
        return companyEdit;
    }



}
