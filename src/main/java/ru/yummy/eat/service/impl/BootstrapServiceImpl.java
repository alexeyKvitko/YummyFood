package ru.yummy.eat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.City;
import ru.yummy.eat.entity.Company;
import ru.yummy.eat.entity.CompanyAction;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.BootstrapModel;
import ru.yummy.eat.model.CompanyActionModel;
import ru.yummy.eat.model.CompanyModel;
import ru.yummy.eat.repo.CityRepository;
import ru.yummy.eat.repo.CompanyActionRepository;
import ru.yummy.eat.repo.CompanyRepository;
import ru.yummy.eat.repo.FeedbackRepository;
import ru.yummy.eat.util.AppUtils;
import ru.yummy.eat.util.ConvertUtils;

import java.util.List;

@Service
public class BootstrapServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(BootstrapServiceImpl.class);

    @Autowired
    CompanyRepository companyRepo;

    @Autowired
    FeedbackRepository feedbackRepo;

    @Autowired
    CityRepository cityRepo;

    @Autowired
    CompanyActionRepository companyActionRepo;

    @Autowired
    ConvertUtils convertUtils;

    @Autowired
    MenuServiceImpl menuService;

    public BootstrapModel getBootstrapModel(String latitude, String longitude) {
        BootstrapModel bootstrapModel = new BootstrapModel();
        Double lat = null;
        Double lon = null;
        List<City> cities = null;
        City city = null;
        City simferopol = null;
        List<Company> companies = null;
        List<CompanyAction> companyActions =  null;
        try {
            simferopol = cityRepo.findById( AppConstants.SIMFEROPOL_ID ).get();
            if (latitude == null || longitude == null ||
             AppConstants.FAKE_STR_ID.equals( latitude ) ||
                    AppConstants.FAKE_STR_ID.equals( longitude ) ) {
                lat = AppConstants.SIMFEROPOL_LAT;
                lon = AppConstants.SIMFEROPOL_LON;
            } else {
                lat = Double.valueOf(latitude.replace(",", "."));
                lon = Double.valueOf(longitude.replace(",", "."));
            }
            cities = cityRepo.findByOrderByName();
            city = AppUtils.getNearestCity(lat, lon, cities);
            if ( city == null ){
                city = simferopol;
            }
            companies = companyRepo.findAllByCityId( city.getId() );
            companyActions = companyActionRepo.findAllByCityId( city.getId() );
            if ( AppUtils.nullOrEmpty( companyActions ) ){
                companyActions = companyActionRepo.findAllByCityId( simferopol.getId() );
            }

        } catch ( Exception e ){
            LOG.error("Exception in method [getBootstrapModel]: "+ e.getMessage() );
        }
        bootstrapModel.setCompanies( convertUtils.convertCompaniesToModelList( companies ) );
        try {
            for( CompanyModel companyModel: bootstrapModel.getCompanies() ){
                Integer feedbackRate = feedbackRepo.getTotalRateByCompanyId( companyModel.getId() );
                companyModel.setFeedbackRate( feedbackRate != null ? feedbackRate : 0 );
                companyModel.setMenuTypeIds( menuService.getMenuTypeIdsAsString( companyModel.getId() ) );
                companyModel.setMenuCategoiesIds( menuService.getMenuCategoryIdsAsString( companyModel.getId() ) );
            }
            bootstrapModel.setFastMenu( menuService.getFastMenu() );
        } catch (BusinessLogicException e ){
            LOG.error("CAN'T GET MENU TYPE IDS, OR MENU CATEGORY IDS"+e.getMessage() );
        }
        bootstrapModel.setCompanyActions( convertUtils.convertCompanyActionsToModels( companyActions ) );
        bootstrapModel.setDeliveryMenu( menuService.getAllMenus() );
        bootstrapModel.setCities( convertUtils.convertCitiesToModelList( cities ) );
        bootstrapModel.setDeliveryCity( city.getName() );
        bootstrapModel.setStaticUrl( AppConstants.STATIC_URL );
        return bootstrapModel;
    }
}
