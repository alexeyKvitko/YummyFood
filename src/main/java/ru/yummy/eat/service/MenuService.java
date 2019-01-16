package ru.yummy.eat.service;

import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.DeliveryMenu;
import ru.yummy.eat.model.MenuCategoryModel;
import ru.yummy.eat.model.MenuTypeModel;

public interface MenuService {

    DeliveryMenu getAllMenus();

    void saveMenuType(MenuTypeModel menuTypeModel) throws BusinessLogicException;

    void saveMenuCategory(MenuCategoryModel menuCategoryModel) throws BusinessLogicException;

    void deleteMenuType(Integer menuTypeId) throws BusinessLogicException;

    void deleteMenuCategory(Integer menuCategoryId) throws BusinessLogicException;
}
