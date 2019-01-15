package ru.yummy.food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.food.AppConstants;
import ru.yummy.food.entity.City;
import ru.yummy.food.entity.Company;
import ru.yummy.food.model.BootstrapModel;
import ru.yummy.food.repo.CityRepository;
import ru.yummy.food.repo.CompanyRepository;
import ru.yummy.food.util.ConvertUtils;

import java.util.List;

@Service
public class BootstrapServiceImpl {

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
        bootstrapModel.setDeliveryMenu( menuService.getAllMenus() );
        bootstrapModel.setCities( convertUtils.convertCitiesToModelList(
                                                        cityRepo.findAllByRegionIdOrderByName( AppConstants.CRIMEA_REGION ) ));
        bootstrapModel.setDeliveryCity( deliveryCity );
        return bootstrapModel;
    }
}
