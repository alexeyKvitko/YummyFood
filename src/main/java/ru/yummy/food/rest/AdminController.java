package ru.yummy.food.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yummy.food.service.impl.CompanyServiceImpl;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/api"})
public class AdminController {


    @Autowired
    CompanyServiceImpl companyService;

    @GetMapping("/getCompanies")
    public List getAllCompanies() {
        return companyService.getAllCompanies();
    }
}
