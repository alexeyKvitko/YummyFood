package ru.yummy.eat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.eat.entity.MenuCategory;
import ru.yummy.eat.entity.MenuType;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.DeliveryMenu;
import ru.yummy.eat.model.MenuCategoryModel;
import ru.yummy.eat.model.MenuTypeModel;
import ru.yummy.eat.repo.MenuCategoryRepository;
import ru.yummy.eat.repo.MenuItemRepository;
import ru.yummy.eat.repo.MenuTypeRepository;
import ru.yummy.eat.service.MenuService;
import ru.yummy.eat.util.ConvertUtils;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuTypeRepository menuTypeRepo;

    @Autowired
    MenuCategoryRepository menuCategoryRepo;

    @Autowired
    MenuItemRepository menuItemRepo;

    @Autowired
    ConvertUtils convertUtils;


    @Override
    public DeliveryMenu getAllMenus() {
        DeliveryMenu deliveryMenu =  new DeliveryMenu();
        List<MenuType> menuTypes = (List<MenuType>) menuTypeRepo.findAllByOrderByDisplayOrder();
        List<MenuCategory> menuCategories = (List<MenuCategory>) menuCategoryRepo.findAllByOrderByDisplayOrder();
        deliveryMenu.setMenuTypes( convertUtils.convertMenuTypesToModelList( menuTypes ) );
        deliveryMenu.setMenuCategories( convertUtils.convertMenuCategoriesToModelList( menuCategories ) );
        return deliveryMenu;
    }

    @Override
    public void saveMenuType(MenuTypeModel menuTypeModel) throws BusinessLogicException {
        try{
            menuTypeRepo.save( convertUtils.convertMenuTypeModelToEntity( menuTypeModel ) );
        } catch (Exception e){
            throw new BusinessLogicException( e.getMessage() );
        }
    }

    @Override
    public void saveMenuCategory(MenuCategoryModel menuCategoryModel) throws BusinessLogicException {
        try{
            menuCategoryRepo.save( convertUtils.convertMenuCategoryModelToEntity( menuCategoryModel ) );
        } catch (Exception e){
            throw new BusinessLogicException( e.getMessage() );
        }
    }

    @Override
    public void deleteMenuType(Integer menuTypeId) throws BusinessLogicException {
        try{
            Integer count = menuItemRepo.getCountByTypeId( menuTypeId );
            if ( count > 0 ){
                throw new BusinessLogicException( "Меню используется, удаление невозможно !" );
            }
            menuTypeRepo.deleteById( menuTypeId );
        } catch (Exception e){
            throw new BusinessLogicException( e.getMessage() );
        }
    }

    @Override
    public void deleteMenuCategory(Integer menuCategoryId) throws BusinessLogicException {
        try{
            Integer count = menuItemRepo.getCountByCategoryId( menuCategoryId );
            if ( count > 0 ){
                throw new BusinessLogicException( "Меню используется, удаление невозможно !" );
            }
            menuCategoryRepo.deleteById( menuCategoryId );
        } catch (Exception e){
            throw new BusinessLogicException( e.getMessage() );
        }
    }
}
