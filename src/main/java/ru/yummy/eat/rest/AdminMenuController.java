package ru.yummy.eat.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.*;
import ru.yummy.eat.service.MenuService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/api/menu"})
public class AdminMenuController {

    @Autowired
    MenuService menuService;

    @GetMapping("/all")
    public DeliveryMenu getAllMenus() {
        return menuService.getAllMenus();
    }

    @RequestMapping(value = "/saveType", method = RequestMethod.POST)
    public ApiResponse saveMenuType(@RequestBody MenuTypeModel menuTypeModel)  {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            menuService.saveMenuType( menuTypeModel );
        } catch (BusinessLogicException e){
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
        return response;
    }

    @RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
    public ApiResponse saveMenuCategory(@RequestBody MenuCategoryModel menuCategoryModel)  {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            menuService.saveMenuCategory( menuCategoryModel );
        } catch (BusinessLogicException e){
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
        return response;
    }

    @GetMapping("/deleteType/{id}")
    public ApiResponse deleteMenuType(@PathVariable Integer id)  {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            menuService.deleteMenuType( id );
        } catch (BusinessLogicException e){
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
        return response;
    }

    @GetMapping("/deleteCategory/{id}")
    public ApiResponse deleteMenucategory(@PathVariable Integer id)  {
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        try {
            menuService.deleteMenuCategory( id );
        } catch (BusinessLogicException e){
            response.setStatus( HttpStatus.INTERNAL_SERVER_ERROR.value() );
            response.setMessage( e.getMessage() );
        }
        return response;
    }

}
