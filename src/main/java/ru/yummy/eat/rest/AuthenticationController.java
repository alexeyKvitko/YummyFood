package ru.yummy.eat.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.yummy.eat.config.JwtTokenUtil;
import ru.yummy.eat.entity.User;
import ru.yummy.eat.model.ApiResponse;
import ru.yummy.eat.model.AuthToken;
import ru.yummy.eat.model.LoginUser;
import ru.yummy.eat.service.UserService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

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
