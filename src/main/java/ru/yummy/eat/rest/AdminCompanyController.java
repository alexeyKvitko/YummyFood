package ru.yummy.eat.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yummy.eat.entity.MenuOrder;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.*;
import ru.yummy.eat.service.ParseService;
import ru.yummy.eat.service.impl.BootstrapServiceImpl;
import ru.yummy.eat.service.impl.CompanyServiceImpl;

import java.util.List;

//@CrossOrigin(origins = "http://yummy-eat.ru", maxAge = 3600)
@CrossOrigin
@RestController
@RequestMapping({"/api"})
public class AdminCompanyController {


    @Autowired
    CompanyServiceImpl companyService;

    @Autowired
    BootstrapServiceImpl bootstrapService;

    @Autowired
    ParseService parseService;

    @GetMapping("/company")
    public List getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/company/bootstrap/{ip}")
    public BootstrapModel getBootsrapInfo(@PathVariable String ip) {
        return bootstrapService.getBootstrapModel(ip);
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

    @GetMapping("/company/addMenu/{companyId}/{typeId}/{categoryId}")
    public ApiResponse addCompanyMenu(@PathVariable int companyId, @PathVariable int typeId,
                                      @PathVariable int categoryId) {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            List<MenuOrder> menuOrders = companyService.addCompanyMenu( companyId,typeId,categoryId );
            response.setResult( menuOrders );
        } catch (BusinessLogicException e){
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
        return response;
    }

    @GetMapping("/company/deleteMenu/{companyId}/{typeId}/{categoryId}")
    public ApiResponse deleteCompanyMenu(@PathVariable int companyId, @PathVariable int typeId,
                                      @PathVariable int categoryId) {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            companyService.deleteCompanyMenu( companyId,typeId,categoryId );
        } catch (BusinessLogicException e){
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
        return response;
    }

    @GetMapping("/company/deleteMenuEntities/{companyId}/{typeId}/{categoryId}")
    public ApiResponse deleteMenuEntities(@PathVariable int companyId, @PathVariable int typeId,
                                         @PathVariable int categoryId) {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            companyService.deleteCompanyMenuEntities( companyId,typeId,categoryId );
        } catch (BusinessLogicException e){
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
        return response;
    }

    @RequestMapping(value = "/testParse", method = RequestMethod.POST)
    public ApiResponse testParseModel(@RequestBody ParseMenuModel parseMenuModel)  {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        parseMenuModel.setParseUrl( parseMenuModel.getParseUrl().replace("https","http") );
        try {
            CompanyMenu companyMenu = parseService.testPage( parseMenuModel );
            if( companyMenu.getParseMenu().isBroken() ){
                response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
                response.setMessage( companyMenu.getParseMenu().getParseResult().getMessage() );
            }
            response.setResult( companyMenu );
        } catch (Exception e){
            response.setMessage( e.getMessage() );
        }
        return response;
    }

    @RequestMapping(value = "/saveParseModel", method = RequestMethod.POST)
    public ApiResponse saveParseModel(@RequestBody ParseMenuModel parseMenuModel)  {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        parseMenuModel.setParseUrl( parseMenuModel.getParseUrl().replace("https","http") );
        try {
            companyService.deleteCompanyMenuEntities(parseMenuModel.getCompanyId(), parseMenuModel.getTypeId(), parseMenuModel.getCategoryId() );
            parseService.saveParseModel( parseMenuModel );
        } catch (BusinessLogicException e){
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
        return response;
    }

    @RequestMapping(value = "/copyParseData", method = RequestMethod.POST)
    public ApiResponse copyParseData(@RequestBody CopyParseData copyParseData)  {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            ParseMenuModel parseMenu = parseService.copyParseData( copyParseData );
            response.setResult( parseMenu );
        } catch (BusinessLogicException e){
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
        return response;
    }

    @RequestMapping(value = "/saveCompany", method = RequestMethod.POST)
    public ApiResponse saveCompanyModel(@RequestBody CompanyModel companyModel )  {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            CompanyEdit companyEdit = companyService.saveCompany( companyModel );
            response.setResult( companyEdit );
        } catch (BusinessLogicException e){
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
        return response;
    }
}
