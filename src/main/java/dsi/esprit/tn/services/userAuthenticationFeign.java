package dsi.esprit.tn.services;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RibbonClient(name="zuul")
@FeignClient(name = "zuul", url = "http://zuul-ms:8765")
public interface userAuthenticationFeign {

    @GetMapping("/authentication-ms/api/test/admin")
    Boolean adminAccess(@RequestHeader(value = "Authorization", required = true) HttpHeaders authorizationHeader);
    @GetMapping("/authentication-ms/api/test/auth")
    String authTest();
    @GetMapping("/authentication-ms/api/test/all")
    String allAccess();
}
