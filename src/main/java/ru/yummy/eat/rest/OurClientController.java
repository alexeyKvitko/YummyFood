package ru.yummy.eat.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.ApiResponse;
import ru.yummy.eat.model.ClientOrderModel;
import ru.yummy.eat.model.OurClientModel;
import ru.yummy.eat.service.impl.OurClientServiceImpl;

@CrossOrigin
@RestController
@RequestMapping({"/api/client"})
public class OurClientController {

    @Autowired
    OurClientServiceImpl clientService;

    @RequestMapping(value = "/registerClient", method = RequestMethod.POST)
    public ApiResponse registerClient(@RequestBody OurClientModel ourClientModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        String result = clientService.registerClient(ourClientModel);
        if ( !result.matches(AppConstants.UUID_PATTERN) ) {
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage(result);
        } else {
            response.setResult( result );
        }
        return response;
    }

    @RequestMapping(value = "/authorizationClient", method = RequestMethod.POST)
    public ApiResponse authorizationClient(@RequestBody OurClientModel ourClientModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        String result = clientService.authorizationClient(ourClientModel);
        if ( !result.matches(AppConstants.UUID_PATTERN) ) {
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage(result);
        } else {
            response.setResult( result );
        }
        return response;
    }

    @RequestMapping(value = "/createClientOrder", method = RequestMethod.POST)
    public ApiResponse createClientOrder(@RequestBody ClientOrderModel clientOrderModel) {
        ApiResponse response = null;
        response = clientService.createClientOrder(clientOrderModel);
        return response;
    }

    @GetMapping("/getClientInfo/{uuid}")
    public ApiResponse deleteMenuType(@PathVariable String uuid)  {
        ApiResponse response = null;
        response = clientService.getClientByUUID( uuid );
        return response;
    }
}
