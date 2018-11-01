package ru.yummy.food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yummy.food.entity.Company;
import ru.yummy.food.repo.CompanyRepository;

import java.util.List;

@Service("companyService")
public class CompanyServiceImpl {

    @Autowired
    CompanyRepository companyRepository;

    public List<Company> getAllCompanies(){
        return (List<Company>) companyRepository.findAll();
    }
}
