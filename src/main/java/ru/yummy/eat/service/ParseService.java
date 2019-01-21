package ru.yummy.eat.service;

import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.CompanyMenu;
import ru.yummy.eat.model.CopyParseData;
import ru.yummy.eat.model.ParseMenuModel;

public interface ParseService {

    void parsePage( );

    CompanyMenu testPage(ParseMenuModel parseMenu) throws BusinessLogicException;

    void saveParseModel(ParseMenuModel parseMenu) throws BusinessLogicException;

    ParseMenuModel copyParseData(CopyParseData copyParseData) throws BusinessLogicException;

}
