package dsi.esprit.tn.services;

import dsi.esprit.tn.Models.ERole;
import dsi.esprit.tn.Models.Role;
import dsi.esprit.tn.Models.User;
import dsi.esprit.tn.Payload.Request.SignupRequest;
import dsi.esprit.tn.Payload.Response.MessageResponse;
import dsi.esprit.tn.repository.RoleRepository;
import dsi.esprit.tn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class userServiceImpl implements IuserServiceImpl{
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public ResponseEntity<?> Signup(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()),signupRequest.getSexe());

        Set<Role> roles = new HashSet<>();
        roles.add(new Role (ERole.ROLE_USER));
        user.setRoles(roles);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @Override
    public User addUser(User user) {
        Set<Role> strRoles = user.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.getName()) {
                    case ROLE_ADMIN:
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case ROLE_MODERATOR:
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
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return userRepository.save(user);
    }
    @Override
    public void deleteUser(long idUser){
        userRepository.deleteById(idUser);
    }
    @Override
    public User updateUser(User user){
        return userRepository.save(user);
    }
    @Override
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User showUser(Long idUser) {
        return userRepository.findById(idUser).orElse(null);

    }
}
