package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.DAO.Entities.*;
import devnatic.danceodyssey.DAO.Repositories.UserRepo;
import devnatic.danceodyssey.Services.IUserServices;
import devnatic.danceodyssey.Services.JwtService;
import devnatic.danceodyssey.Services.UserInfoService;
import devnatic.danceodyssey.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@AllArgsConstructor()

public class Controller {
    UserRepo userRepo;
    @Autowired
    private IUserServices services;
    @Autowired
    private UserService service;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserInfoService serviceUser;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/addUser")
    public User addUser( @RequestBody User user) {
        return services.adduser(user);}
    @PostMapping("/addadmin")
    public Admin addadmin (@RequestBody Admin admin ){
        return services.addadmin(admin);


    }
    @DeleteMapping("/deleteUser/{userID}")
    public void deleteUser(@PathVariable("userID") Long userID) {
        services.deleteUser(userID);
    }
    @GetMapping("/showusers")
    public List<User> showUsers(){
        return services.getAllUsers();
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest loginRequest) {
        User user = services.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            System.err.println(user.toString());
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/generateToken")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        AuthResponse response = new AuthResponse();
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            // If authentication successful, generate JWT token
            String token = jwtService.generateToken(authRequest.getUsername());
            response.setToken(token);
            User user = services.laodByEmail(authRequest.getUsername());
            response.setUserName(user.getUsername());
            response.setRole(user.getRole());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // If authentication fails, return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @PostMapping("/addNewUser")
    public User addNewUser(@RequestBody User userInfo) {
        System.err.println(userInfo.toString());
        System.err.println(userInfo.getRole().toString());

        return serviceUser.addUser(userInfo);
    }



    @GetMapping("/getUser/{name}")
    public User getUser(@PathVariable("name")String username){
        return userRepo.findByUserName(username);
    }
}





