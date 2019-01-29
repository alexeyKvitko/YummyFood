package ru.yummy.eat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.*;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.*;
import ru.yummy.eat.repo.*;
import ru.yummy.eat.util.ConvertUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("companyService")
public class CompanyServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    CompanyRepository companyRepo;

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
    MenuOrderRepository orderRepo;

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
        List<MenuType> menuTypes = menuTypeRepo.findTypesByCompanyIdOrderByDisplayName(Integer.valueOf(companyId));
        for (MenuType menuType : menuTypes) {
            MenuTypeModel menuTypeModel = convertUtils.convertMenuTypeToModel(menuType);
            List<MenuCategoryModel> menuCategoryModels = new ArrayList<>();
            List<MenuCategory> categories = menuCategoryRepo.findCategoriesByCompanyAndTypeIdOrderByDisplayName(Integer.valueOf(companyId),
                    menuType.getId());
            for (MenuCategory category : categories) {
                menuCategoryModels.add(convertUtils.convertMenuCategoryToModel(category, menuType.getId()));
                menuEntityModels.addAll( getCompanyMenuModels( companyId, menuType.getId(), category.getId()) );
            }
            menuTypeModel.setMenuCategories(menuCategoryModels);
            menuTypeModels.add(menuTypeModel);
        }
        List<MenuOrder> menuOrders = orderRepo.findAllByCompanyId( companyId );
        if ( menuOrders != null && menuOrders.size() > 0 ){
            for( MenuTypeModel menuTypeModel : menuTypeModels ){
                Optional<MenuOrder>  menuOrder=  menuOrders.stream().filter(order ->
                        ( order.getMenuTypeId().equals( menuTypeModel.getId() ) ) ).findFirst();
                if( menuOrder.isPresent() ){
                    menuTypeModel.setOrder( menuOrder.get().getOrder() );
                } else {
                    menuTypeModel.setOrder( AppConstants.FAKE_ID );
                }
                if( menuOrder.isPresent() ){
                    for( MenuCategoryModel menuCategoryModel: menuTypeModel.getMenuCategories() ){
                        menuOrder = menuOrders.stream().filter( order -> (
                                order.getMenuTypeId().equals( menuTypeModel.getId() ) &&
                                        order.getMenuCategoryId().equals( menuCategoryModel.getId() ) ) )
                                .findFirst();
                        if( menuOrder.isPresent() ){
                            menuCategoryModel.setOrder( menuOrder.get().getOrder() );
                        } else {
                            menuTypeModel.setOrder( AppConstants.FAKE_ID );
                        }
                    }
                    Collections.sort( menuTypeModel.getMenuCategories(), new Comparator<MenuCategoryModel>() {
                        @Override
                        public int compare(MenuCategoryModel o1, MenuCategoryModel o2) {
                            return (int) (o1.getOrder() - o2.getOrder());
                        }
                    } );
                }

            }
            Collections.sort( menuTypeModels, new Comparator<MenuTypeModel>() {
                @Override
                public int compare(MenuTypeModel o1, MenuTypeModel o2) {
                    return (int) (o1.getOrder() - o2.getOrder());
                }
            } );
            for( MenuOrder menuOrder: menuOrders ){
                Integer typeOrder = menuOrders.stream().filter( order -> (order.getMenuTypeId().equals( menuOrder.getMenuTypeId() ) &&
                                        AppConstants.FAKE_ID.equals( order.getMenuCategoryId() ) ) ).findFirst().get().getOrder();
                for( MenuEntityModel menuEntityModel : menuEntityModels ){
                    if( menuOrder.getMenuTypeId().equals( menuEntityModel.getTypeId() ) &&
                            menuOrder.getMenuCategoryId().equals( menuEntityModel.getCategoryId() ) ){
                        menuEntityModel.setTypeOrder( typeOrder );
                        menuEntityModel.setCategoryOrder( menuOrder.getOrder() );
                    }
                }
            }
            Comparator<MenuEntityModel> comparator = Comparator.comparing(menuEntityModel -> menuEntityModel.getTypeOrder());
            comparator = comparator.thenComparing(Comparator.comparing(menuEntityModel -> menuEntityModel.getCategoryOrder()));
            Stream<MenuEntityModel> stream = menuEntityModels.stream().sorted(comparator);
            menuEntityModels = stream.collect( Collectors.toList() );
        }
        companyInfo.setCompanyModel(company);
        companyInfo.setMenuTypes(menuTypeModels);
        companyInfo.setMenuEntities( menuEntityModels);
        return companyInfo;
    }

    public CompanyEdit getCompanyEdit(Integer companyId) {
        CompanyEdit companyEdit =  new CompanyEdit();
        CompanyInfo companyInfo = getCompanyInfo( companyId );
        companyEdit.setCompanyModel( companyInfo.getCompanyModel() );
        companyEdit.setMenuTypes( companyInfo.getMenuTypes() );
        companyEdit.setMenuOrders(orderRepo.findAllByCompanyId( companyId ) );
        companyEdit.setDeliveryMenuTypes( convertUtils.convertMenuTypesToModelList( (List<MenuType>) menuTypeRepo.findAllByOrderByDisplayName() ) );
        companyEdit.setDeliveryMenuCategories( convertUtils.convertMenuCategoriesToModelList( (List<MenuCategory>) menuCategoryRepo.findAllByOrderByDisplayName() ) );
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

    public List<MenuOrder> addCompanyMenu(int companyId, int typeId, int categoryId) throws BusinessLogicException {
        List<MenuOrder> menuOrders = null;
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
            MenuOrder menuTypeOrder = orderRepo.findByCompanyIdAndMenuTypeIdAndMenuCategoryId( companyId, typeId , AppConstants.FAKE_ID);
            if( menuTypeOrder == null ){
                Integer maxMenuTypeOrder = orderRepo.getMaxTypeOrder( companyId );
                maxMenuTypeOrder = maxMenuTypeOrder != null ? maxMenuTypeOrder+1000 : 1000;
                orderRepo.save( new MenuOrder( companyId,typeId, AppConstants.FAKE_ID, maxMenuTypeOrder ) );
            }
            Integer maxMenuCategoryOrder = orderRepo.getMaxCategoryOrder( companyId, typeId );
            maxMenuCategoryOrder = maxMenuCategoryOrder != null ? maxMenuCategoryOrder+1 : 1;
            orderRepo.save( new MenuOrder( companyId,typeId,categoryId, maxMenuCategoryOrder ) );
            parseRepo.save( parseMenu );
            menuItemRepo.save( menuItem );
            menuOrders = orderRepo.findAllByCompanyId( companyId );
        } catch (Exception e){
            throw new BusinessLogicException( "Добавление невозможно, "+e.getMessage() );
        }
        return menuOrders;
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

    public void deleteCompanyMenuEntities(int companyId, int typeId, int categoryId) throws BusinessLogicException {
        try {
            menuEntityRepo.deleteMenuEntities( companyId, typeId, categoryId );
            menuItemRepo.deleteMenuItems( companyId, typeId, categoryId );
        } catch (Exception e){
            throw new BusinessLogicException( "Ошибка при попытке удаления, "+e.getMessage() );
        }
    }

    public CompanyEdit saveCompany(CompanyModel companyModel ) throws BusinessLogicException {
        CompanyEdit companyEdit = null;
        Company company = convertUtils.convertCompanyModelToEntity( companyModel );
        try {
            companyRepo.save( company );
            if ( company.getId() != null ){
              companyEdit = getCompanyEdit( company.getId() );
            }
        } catch (Exception e){
            LOG.error("Ошибка при попытке записи компании, "+e.getMessage());
            throw new BusinessLogicException( "Ошибка при попытке записи компании, "+e.getMessage() );
        }
        return companyEdit;
    }



}
