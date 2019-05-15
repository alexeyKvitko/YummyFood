package ru.yummy.eat.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.yummy.eat.config.JwtTokenUtil;
import ru.yummy.eat.entity.User;
import ru.yummy.eat.exception.BusinessLogicException;
import ru.yummy.eat.model.ApiResponse;
import ru.yummy.eat.model.AuthToken;
import ru.yummy.eat.model.LoginUser;
import ru.yummy.eat.service.UserService;
import ru.yummy.eat.service.impl.OurClientServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
    public String helloWorld(){
        final User user = userService.findOne("admin");
        return "HELLO WORLD, "+(user.getLastName()+" "+user.getFirstName()+" "+user.getSecondName());
    }


    @RequestMapping(value = "/successMerchant", method = RequestMethod.GET)
    public ApiResponse successMerchant(){
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        response.setMessage( "Success Merchant" );
        LOG.info( "Success Merchant" );
        return response;
    }

    @RequestMapping(value = "/failMerchant", method = RequestMethod.GET)
    public ApiResponse failMerchant(){
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        response.setMessage( "Fail Merchant" );
        LOG.info( "Fail Merchant" );
        return response;
    }

    @RequestMapping(value = "/handlerMerchant", method = RequestMethod.GET)
    public ApiResponse handlerMerchant(){
        ApiResponse response = new ApiResponse();
        response.setStatus( HttpStatus.OK.value() );
        response.setMessage( "Handler Merchant" );
        LOG.info( "Handler Merchant" );
        return response;
    }

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        } catch (Exception e){
            System.out.println("Exception: "+e);
            return new ApiResponse<>(401, "Unauthorized", null);
        }
        final User user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        AuthToken authToken = new AuthToken();
        authToken.setToken( token );
        authToken.setUsername( user.getLastName()+" "+user.getFirstName()+" "+user.getSecondName() );
        authToken.setUserRole( user.getRole() );
        return new ApiResponse<>(200, "success", authToken);
    }

}
