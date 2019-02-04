package ru.yummy.eat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.City;
import ru.yummy.eat.entity.Company;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.BootstrapModel;
import ru.yummy.eat.model.CompanyModel;
import ru.yummy.eat.repo.CityRepository;
import ru.yummy.eat.repo.CompanyRepository;
import ru.yummy.eat.util.ConvertUtils;

import java.util.List;

@Service
public class BootstrapServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(BootstrapServiceImpl.class);

    @Autowired
    CompanyRepository companyRepo;

    @Autowired
    CityRepository cityRepo;

    @Autowired
    ConvertUtils convertUtils;

    @Autowired
    MenuServiceImpl menuService;

    public BootstrapModel getBootstrapModel(String cityName) {
        BootstrapModel bootstrapModel = new BootstrapModel();
        List<Company> companies = null;
        City city = null;
        Integer cityId = AppConstants.SIMFEROPOL_ID;
        String deliveryCity = AppConstants.SIMFEROPOL_NAME;
        bootstrapModel.setDefault( true );
        try{
            city = cityRepo.findByNameEn( cityName );
        } catch (Exception e){
            //TODo LOG
        }
        if  ( city != null ){
            cityId = city.getId();
            deliveryCity = city.getName();
            bootstrapModel.setDefault( false );
        }
        if( cityName == null ){
            companies = (List<Company>) companyRepo.findAll();
        } else {
            companies= companyRepo.findAllByCityId( cityId );
        }
        bootstrapModel.setCompanies( convertUtils.convertCompaniesToModelList( companies ) );
        try {
            for( CompanyModel companyModel: bootstrapModel.getCompanies() ){
                companyModel.setMenuTypeIds( menuService.getMenuTypeIdsAsString( companyModel.getId() ) );
                companyModel.setMenuCategoiesIds( menuService.getMenuCategoryIdsAsString( companyModel.getId() ) );
            }
        } catch (BusinessLogicException e ){
            LOG.error("CAN'T GET MENU TYPE IDS, OR MENU CATEGORY IDS"+e.getMessage() );
        }

        bootstrapModel.setDeliveryMenu( menuService.getAllMenus() );
        bootstrapModel.setCities( convertUtils.convertCitiesToModelList(
                                                        cityRepo.findAllByRegionIdOrderByName( AppConstants.CRIMEA_REGION ) ));
        bootstrapModel.setDeliveryCity( deliveryCity );
        return bootstrapModel;
    }
}
