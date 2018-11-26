package ru.yummy.food.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.yummy.food.entity.User;
import ru.yummy.food.model.*;
import ru.yummy.food.service.impl.CompanyServiceImpl;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/api"})
public class AdminController {


    @Autowired
    CompanyServiceImpl companyService;

    @GetMapping("/company")
    public List getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/company/{id}")
    public CompanyInfo getAllCompanies(@PathVariable int id) {
        return companyService.getCompanyInfo(id);
    }

    @GetMapping("/company/{companyId}/{typeId}/{categoryId}")
    public CompanyMenu getCompanyMenu(@PathVariable int companyId, @PathVariable int typeId,
                                      @PathVariable int categoryId) {
        return companyService.getCompanyMenu( companyId,typeId,categoryId );
    }

    @RequestMapping(value = "/testParse", method = RequestMethod.POST)
    public CompanyMenu testParseModel(@RequestBody ParseMenuModel parseMenuModel)  {
        return new CompanyMenu();
    }
}
