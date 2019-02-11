package ru.yummy.eat.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.ApiResponse;
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
        if (!AppConstants.SUCCESS.equals(result)) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(result);
        }
        return response;
    }
}
