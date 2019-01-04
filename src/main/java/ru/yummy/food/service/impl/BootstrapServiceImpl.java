package ru.yummy.food.service.impl;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.food.AppConstants;
import ru.yummy.food.entity.City;
import ru.yummy.food.entity.CompanyShort;
import ru.yummy.food.model.BootstrapModel;
import ru.yummy.food.model.DeliveryMenu;
import ru.yummy.food.model.IpInfo;
import ru.yummy.food.repo.CityRepository;
import ru.yummy.food.repo.CompanyShortRepository;
import ru.yummy.food.util.ConvertUtils;

import java.util.List;

@Service
public class BootstrapServiceImpl {

    @Autowired
    CompanyShortRepository companyShortRepo;

    @Autowired
    CityRepository cityRepo;

    @Autowired
    ConvertUtils convertUtils;

    @Autowired
    MenuServiceImpl menuService;

    public BootstrapModel getBootstrapModel(String cityName) {
        BootstrapModel bootstrapModel = new BootstrapModel();
        List<CompanyShort> companies = null;
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
        companies= companyShortRepo.findAllByCityId( cityId );
        if( cityName == null ){
            companies = (List<CompanyShort>) companyShortRepo.findAll();
        } else {
            companies= companyShortRepo.findAllByCityId( cityId );
        }
        bootstrapModel.setCompanyShorts( companies );
        bootstrapModel.setDeliveryMenu( menuService.getAllMenus() );
        bootstrapModel.setCities( convertUtils.convertCitiesToModelList(
                                                        cityRepo.findAllByRegionIdOrderByName( AppConstants.CRIMEA_REGION ) ));
        bootstrapModel.setDeliveryCity( deliveryCity );
        return bootstrapModel;
    }
}
