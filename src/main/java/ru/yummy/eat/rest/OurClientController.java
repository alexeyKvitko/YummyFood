package ru.yummy.eat.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.model.*;
import ru.yummy.eat.service.impl.OurClientServiceImpl;
import ru.yummy.eat.service.impl.PayeerServiceImpl;
import ru.yummy.eat.service.impl.SmsServiceImpl;

@CrossOrigin
@RestController
@RequestMapping({"/api/client"})
public class OurClientController {

    private static final Logger LOG = LoggerFactory.getLogger(OurClientController.class);

    @Autowired
    OurClientServiceImpl clientService;

    @Autowired
    SmsServiceImpl smsService;

    @Autowired
    PayeerServiceImpl payeerService;

    @RequestMapping(value = "/registerClient", method = RequestMethod.POST)
    public ApiResponse registerClient(@RequestBody OurClientModel ourClientModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        String result = clientService.registerClient(ourClientModel);
        if (!result.matches(AppConstants.UUID_PATTERN)) {
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage(result);
        } else {
            response.setResult(result);
        }
        return response;
    }

    @RequestMapping(value = "/registerMobileClient", method = RequestMethod.POST)
    public ApiResponse registerMobileClient(@RequestBody OurClientModel ourClientModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        OurClientModel newClient = clientService.registerMobileClient(ourClientModel);
        if (AppConstants.FAKE_ID.equals(newClient.getId())) {
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage(newClient.getAdditionalMessage());
        } else {
            response.setResult(newClient);
        }
        return response;
    }


    @RequestMapping(value = "/validateAndSendEmail", method = RequestMethod.POST)
    public ApiResponse validateAndSendEmail(@RequestBody OurClientModel ourClientModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        String result = clientService.validateAndSendEmailConfirmCode(ourClientModel);
        if (!result.matches(AppConstants.CONFIRM_CODE_PATTERN)) {
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage(result);
        } else {
            response.setResult(result);
        }
        return response;
    }

    @RequestMapping(value = "/authorizationClient", method = RequestMethod.POST)
    public ApiResponse authorizationClient(@RequestBody OurClientModel ourClientModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        OurClientModel result = clientService.authorizationClient(ourClientModel);
        if ( result.getAdditionalMessage() != null ) {
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage( result.getAdditionalMessage() );
        } else {
            response.setResult(result);
        }
        return response;
    }

    @RequestMapping(value = "/authorizationMobileClient", method = RequestMethod.POST)
    public ApiResponse authorizationMobileClient(@RequestBody OurClientModel ourClientModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        OurClientModel existClient = clientService.authorizationMobileClient(ourClientModel);
        if ( existClient.getAdditionalMessage() != null ) {
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage(existClient.getAdditionalMessage());
            response.setResult( null );
        } else {
            response.setResult( existClient );
        }
        return response;
    }

    @RequestMapping(value = "/updateClientInfo", method = RequestMethod.POST)
    public ApiResponse updateClientInfo(@RequestBody OurClientModel ourClientModel) {
        return clientService.updateClientInfo( ourClientModel );
    }

    @RequestMapping(value = "/updateClientPayType", method = RequestMethod.POST)
    public ApiResponse updateClientPayType(@RequestBody OurClientModel ourClientModel) {
        return clientService.updateClientPayType( ourClientModel );
    }

    @RequestMapping(value = "/updateClientAddress", method = RequestMethod.POST)
    public ApiResponse updateClientInfo(@RequestBody ClientLocationModel clientLocationModel) {
        return clientService.updateClientAddress(clientLocationModel);
    }


    @RequestMapping(value = "/updateClientPassword", method = RequestMethod.POST)
    public ApiResponse updateClientPassword(@RequestBody OurClientModel ourClientModel) {
        return clientService.updateClientPassword( ourClientModel );
    }


    @GetMapping("/getClientInfo/{uuid}")
    public ApiResponse getClientInfo(@PathVariable String uuid) {
        ApiResponse response = null;
        response = clientService.getClientByUUID(uuid);
        return response;
    }

    @GetMapping("/removeClient/{uuid}")
    public ApiResponse removeClient(@PathVariable String uuid) {
        ApiResponse response = null;
        response = clientService.removeClientFully( uuid );
        return response;
    }

    @RequestMapping(value = "/addToFavorite", method = RequestMethod.POST)
    public ApiResponse addCompanyToFavorite(@RequestBody FavoriteCompanyModel favoriteCompanyModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        response = clientService.addToFavorite(favoriteCompanyModel);
        return response;
    }

    @RequestMapping(value = "/removeFromFavorite", method = RequestMethod.POST)
    public ApiResponse removeCompanyFromFavorite(@RequestBody FavoriteCompanyModel favoriteCompanyModel) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        response = clientService.removeFromFavorite(favoriteCompanyModel);
        return response;
    }

    @GetMapping("/sendEmailToUs/{message}")
    public ApiResponse sendEmailToUs(@PathVariable String message) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        String result = clientService.sendEmailToUs(message);
        if (AppConstants.UNEXPECTED_ERROR.equals(result)) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.setMessage(result);
        return response;
    }

    @GetMapping("/sendSmsCode/{phone}")
    public ApiResponse sendSmsCodeToClient(@PathVariable String phone) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        String result = smsService.send( phone );
        if ( result == null ) {
            result = AppConstants.CODE_NOT_SEND;
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.setMessage(result);
        return response;
    }

    @GetMapping("/getPayeerUrl/{orderId}/{amount}/{date}")
    public ApiResponse getPayeerUrl(@PathVariable String orderId,@PathVariable String amount,
                                    @PathVariable String date) {
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        String result = payeerService.generatePayeerPaymentURL( orderId, amount, date );
        if ( result == null ) {
            result = AppConstants.UNEXPECTED_ERROR;
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.setMessage(result);
        return response;
    }

    @RequestMapping(value = "/updateClientAvatar", method = RequestMethod.POST)
    public ApiResponse updateClientAvatar(@RequestParam("clientAvatar") MultipartFile clientAvatar,
                                          @RequestPart String description) {
        LOG.info("------------------- Original FileName: "+ clientAvatar.getOriginalFilename() );
        ApiResponse response = clientService.updateClientAvatar( description.replaceAll("\"",""), clientAvatar );
        LOG.info("Response Message: "+response.getMessage());
        return response;
    }

}
