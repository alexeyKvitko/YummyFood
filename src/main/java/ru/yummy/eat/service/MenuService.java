package ru.yummy.eat.service;

import ru.yummy.eat.entity.MenuOrder;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.DeliveryMenu;
import ru.yummy.eat.model.MenuCategoryModel;
import ru.yummy.eat.model.MenuTypeModel;

import java.util.List;

public interface MenuService {

    DeliveryMenu getAllMenus();

    void saveMenuType(MenuTypeModel menuTypeModel) throws BusinessLogicException;

    void saveMenuCategory(MenuCategoryModel menuCategoryModel) throws BusinessLogicException;

    void deleteMenuType(Integer menuTypeId) throws BusinessLogicException;

    void deleteMenuCategory(Integer menuCategoryId) throws BusinessLogicException;

    void saveMenuOrders(List<MenuOrder> menuOrders) throws BusinessLogicException;

    String getMenuTypeIdsAsString(Integer companyId) throws BusinessLogicException;

    String getMenuCategoryIdsAsString(Integer companyId) throws BusinessLogicException;
}
