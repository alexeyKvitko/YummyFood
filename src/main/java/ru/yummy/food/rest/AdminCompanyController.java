package ru.yummy.food.rest;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yummy.food.exception.BusinessLogicException;
import ru.yummy.food.model.*;
import ru.yummy.food.service.ParseService;
import ru.yummy.food.service.impl.CompanyServiceImpl;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/api"})
public class AdminCompanyController {


    @Autowired
    CompanyServiceImpl companyService;

    @Autowired
    ParseService parseService;

    @GetMapping("/company")
    public List getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/company/edit/{id}")
    public CompanyEdit getCompanyEditById(@PathVariable int id) {
        return companyService.getCompanyEdit(id);
    }

    @GetMapping("/company/{id}")
    public CompanyInfo getCompanyInfoById(@PathVariable int id) {
        return companyService.getCompanyInfo(id);
    }

    @GetMapping("/company/{companyId}/{typeId}/{categoryId}")
    public CompanyMenu getCompanyMenu(@PathVariable int companyId, @PathVariable int typeId,
                                      @PathVariable int categoryId) {
        return companyService.getCompanyMenu( companyId,typeId,categoryId );
    }

    @RequestMapping(value = "/testParse", method = RequestMethod.POST)
    public CompanyMenu testParseModel(@RequestBody ParseMenuModel parseMenuModel)  {
        return parseService.testPage( parseMenuModel );
    }

    @RequestMapping(value = "/saveParseModel", method = RequestMethod.POST)
    public ApiResponse saveParseModel(@RequestBody ParseMenuModel parseMenuModel)  {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            parseService.saveParseModel( parseMenuModel );
        } catch (BusinessLogicException e){
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
        return response;
    }
}
