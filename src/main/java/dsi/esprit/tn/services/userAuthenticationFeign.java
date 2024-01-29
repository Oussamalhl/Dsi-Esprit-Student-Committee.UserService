package dsi.esprit.tn.services;

import dsi.esprit.tn.Payload.Request.LoginRequest;
import dsi.esprit.tn.Payload.Request.SignupRequest;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
@RibbonClient(name="zuul")
@FeignClient(name = "zuul", url = "http://zuul-ms:8765")
public interface userAuthenticationFeign {

    @GetMapping("/authentication-ms/api/test/admin")
    Boolean adminAccess(@RequestHeader(value = "Authorization", required = true) HttpHeaders authorizationHeader);
    @GetMapping("/authentication-ms/api/test/auth")
    String authTest();
    @PostMapping("/authentication-ms/api/auth/signin")
    ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping("/authentication-ms/api/auth/signup")
    ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest);
    @GetMapping("/authentication-ms/api/test/all")
    String allAccess();
}
