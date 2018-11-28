package ru.yummy.food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.food.entity.MenuCategory;
import ru.yummy.food.entity.MenuType;
import ru.yummy.food.exception.BusinessLogicException;
import ru.yummy.food.model.DeliveryMenu;
import ru.yummy.food.model.MenuCategoryModel;
import ru.yummy.food.model.MenuTypeModel;
import ru.yummy.food.repo.MenuCategoryRepository;
import ru.yummy.food.repo.MenuItemRepository;
import ru.yummy.food.repo.MenuTypeRepository;
import ru.yummy.food.service.MenuService;
import ru.yummy.food.util.ConvertUtils;

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
        List<MenuType> menuTypes = (List<MenuType>) menuTypeRepo.findAll();
        List<MenuCategory> menuCategories = (List<MenuCategory>) menuCategoryRepo.findAll();
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
