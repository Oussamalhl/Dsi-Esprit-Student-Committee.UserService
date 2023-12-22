package dsi.esprit.tn.Controllers;

import dsi.esprit.tn.Payload.Request.LoginRequest;
import dsi.esprit.tn.Payload.Request.SignupRequest;
import dsi.esprit.tn.Payload.Response.MessageResponse;
import dsi.esprit.tn.repository.UserRepository;
import dsi.esprit.tn.services.userAuthenticationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestHeader;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/user")
public class userServiceController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private userAuthenticationFeign userAuthenticationFeign;

    @GetMapping("/test")
    public String test(){
        return "user api.";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return userAuthenticationFeign.authenticateUser(loginRequest);
    }

    @GetMapping("/api/test/all")
    public String allAccess(){
        return userAuthenticationFeign.allAccess();
    }
    @GetMapping("/api/test/admin")
    public String adminAccess(@RequestHeader(value = "Authorization", required = true) HttpHeaders authorizationHeader){
        return userAuthenticationFeign.adminAccess(authorizationHeader);
    }
    @GetMapping("/api/test/auth")
    public String authTest() {
        return userAuthenticationFeign.authTest();
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestHeader(value = "Authorization", required = true) HttpHeaders authorizationHeader,
                                     @Valid@RequestBody SignupRequest signupRequest){

        if(userAuthenticationFeign.adminAccess(authorizationHeader).contains("admin"))
           return userAuthenticationFeign.registerUser(signupRequest);

        else
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Must be admin"));
    }
}
