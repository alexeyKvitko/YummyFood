package ru.yummy.food.service;

import ru.yummy.food.entity.MenuType;
import ru.yummy.food.exception.BusinessLogicException;
import ru.yummy.food.model.DeliveryMenu;
import ru.yummy.food.model.MenuCategoryModel;
import ru.yummy.food.model.MenuTypeModel;

public interface MenuService {

    DeliveryMenu getAllMenus();

    void saveMenuType(MenuTypeModel menuTypeModel) throws BusinessLogicException;

    void saveMenuCategory(MenuCategoryModel menuCategoryModel) throws BusinessLogicException;

    void deleteMenuType(Integer menuTypeId) throws BusinessLogicException;

    void deleteMenuCategory(Integer menuCategoryId) throws BusinessLogicException;
}
