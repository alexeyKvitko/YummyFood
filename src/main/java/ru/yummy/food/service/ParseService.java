package ru.yummy.food.service;

import ru.yummy.food.exception.BusinessLogicException;
import ru.yummy.food.model.CompanyMenu;
import ru.yummy.food.model.ParseMenuModel;

public interface ParseService {

    void parsePage( );

    CompanyMenu testPage(ParseMenuModel parseMenu) throws BusinessLogicException;

    void saveParseModel(ParseMenuModel parseMenu) throws BusinessLogicException;

}
