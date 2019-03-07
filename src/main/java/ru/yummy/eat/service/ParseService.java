package ru.yummy.eat.service;

import ru.yummy.eat.entity.ParseMenu;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.CompanyMenu;
import ru.yummy.eat.model.CopyParseData;
import ru.yummy.eat.model.ParseMenuModel;

public interface ParseService {

    void parsePage( ParseMenu parseMenu );

    CompanyMenu testPage(ParseMenuModel parseMenu, boolean isUpdateJournal) throws BusinessLogicException;

    void saveParseModel(ParseMenuModel parseMenu) throws BusinessLogicException;

    ParseMenuModel copyParseData(CopyParseData copyParseData) throws BusinessLogicException;

    void scheduledTestParseMenu();

    void scheduledProductionParseMenu();

}
