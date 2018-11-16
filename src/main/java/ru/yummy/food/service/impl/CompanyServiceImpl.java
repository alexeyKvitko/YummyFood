package ru.yummy.food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.food.entity.City;
import ru.yummy.food.entity.Company;
import ru.yummy.food.model.CompanyModel;
import ru.yummy.food.repo.CityRepository;
import ru.yummy.food.repo.CompanyRepository;

import java.util.ArrayList;
import java.util.List;

@Service("companyService")
public class CompanyServiceImpl {

    @Autowired
    CompanyRepository companyRepo;

    @Autowired
    CityRepository cityRepo;

    public List<CompanyModel> getAllCompanies(){
        List<Company> companies = (List<Company>) companyRepo.findAll();
        List<CompanyModel> models = new ArrayList<>();
        for( Company company: companies ){
            models.add( convertToModel( company ) );
        }
        return models;
    }

    private CompanyModel convertToModel(Company company){
        CompanyModel companyModel = new CompanyModel();
        companyModel.setId( company.getId() );
        companyModel.setCompanyName( company.getCompanyName() );
        companyModel.setDisplayName( company.getDisplayName() );
        City city = cityRepo.findById( company.getCityId() ).get();
        companyModel.setCity( city );
        companyModel.setUrl( company.getUrl() );
        companyModel.setPhoneOne( company.getPhoneOne() );
        companyModel.setPhoneTwo( company.getPhoneTwo() );
        companyModel.setPhoneThree( company.getPhoneThree() );
        return companyModel;
    }
}
