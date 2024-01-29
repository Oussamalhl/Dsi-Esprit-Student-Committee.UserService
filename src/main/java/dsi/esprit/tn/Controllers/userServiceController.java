package dsi.esprit.tn.Controllers;

import dsi.esprit.tn.Models.ERole;
import dsi.esprit.tn.Models.Role;
import dsi.esprit.tn.Models.User;
import dsi.esprit.tn.Payload.Request.LoginRequest;
import dsi.esprit.tn.Payload.Request.SignupRequest;
import dsi.esprit.tn.Payload.Response.MessageResponse;
import dsi.esprit.tn.repository.RoleRepository;
import dsi.esprit.tn.repository.UserRepository;
import dsi.esprit.tn.services.IuserServiceImpl;
import dsi.esprit.tn.services.userAuthenticationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestHeader;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/user")
public class userServiceController {

    @Autowired
    IuserServiceImpl userservice;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private userAuthenticationFeign userAuthenticationFeign;

    @GetMapping("/auth/test/all")
    public String allAccess(){
        return userAuthenticationFeign.allAccess();
    }
    @GetMapping("/auth/test/admin")
    public Boolean adminAccess(@RequestHeader(value = "Authorization", required = true) HttpHeaders authorizationHeader){
        return userAuthenticationFeign.adminAccess(authorizationHeader);
    }
    @GetMapping("/auth/test")
    public String authTest() {
        return userAuthenticationFeign.authTest();
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return userAuthenticationFeign.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "ROLE_MODERATOR":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

/////////////////////

    @GetMapping("/test")
    public String test(){
        return "user api.";
    }
    @GetMapping("/test/auth")
    public String testAuth(){
        return "user authenticated.";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Boolean adminTest() {
        return true;
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public Boolean moderatorTest() {
        return true;
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> addUser(@RequestHeader(value = "Authorization", required = true) HttpHeaders authorizationHeader,
                                     @Valid@RequestBody SignupRequest signupRequest){

        if(userAuthenticationFeign.adminAccess(authorizationHeader)){
            userAuthenticationFeign.registerUser(signupRequest);
            return ResponseEntity.ok("User: "+signupRequest+" is successfully added");
        }
        else return ResponseEntity.badRequest()
                .body(new MessageResponse("Must be admin"));

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser (@Valid@RequestParam long idUser){

            userservice.deleteUser(idUser);
            return ResponseEntity.ok("User Id:"+idUser+" is successfully deleted");
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/showallUser")
    public ResponseEntity<?> showAllUsers (){

            return ResponseEntity.ok(userservice.showAllUsers());
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/showUser")
    public ResponseEntity<?> showUser (@Valid@RequestParam long idUser){

            return ResponseEntity.ok(userservice.showUser(idUser));

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser (@RequestBody User user){

            userservice.updateUser(user);
            return ResponseEntity.ok("Userid: "+user.getId()+" is successfully updated");

    }
}
