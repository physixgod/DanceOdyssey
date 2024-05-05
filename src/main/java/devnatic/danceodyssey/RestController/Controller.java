package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.DAO.Entities.*;

import devnatic.danceodyssey.DAO.Repositories.JuryRepo;
import devnatic.danceodyssey.DAO.Repositories.PaymentInfoRepository;

import devnatic.danceodyssey.DAO.Repositories.ProductRepository;

import devnatic.danceodyssey.DAO.Repositories.UserRepo;
import devnatic.danceodyssey.Interfaces.IParentCategoryService;
import devnatic.danceodyssey.Services.*;
import devnatic.danceodyssey.utils.CodeGenerator;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@AllArgsConstructor()

public class Controller {
    @Autowired
    private JuryRepo juryRepo;
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
    @Autowired
    private EmailService emailService;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository ;
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IParentCategoryService iParentCategoryService;



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
            response.setEmail(user.getEmail()); // Set the email
            response.setUserName(user.getUsername());
            response.setUserID(Long.valueOf(String.valueOf(user.getUserID()))); // Set the userID as a string
            response.setRole(user.getRole());
            response.setStatus(user.getStatus());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // If authentication fails, return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }



    @PostMapping("/addNewUser")
    public User addNewUser(@RequestBody User userInfo) {


        return serviceUser.addUser(userInfo);
    }



    @GetMapping("/getUser/{name}")
    public User getUser(@PathVariable("name")String username){
        return userRepo.findByUserName(username);
    }

    @GetMapping ("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email,@RequestParam("code") String code) throws MessagingException, jakarta.mail.MessagingException {
        // Log the email received from the request
        System.out.println("Email received from request: " + email);

        // Retrieve user from the database
        User user = userRepo.findByEmail(email);
        //
        System.out.println("dedhedenehdn"+user.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with the provided email");
        }

        // Generate a random code


        // Save the reset code in the database (you may need to modify your User entity)

        // Send the reset code via email
        emailService.sendEmail(email,code);

        return ResponseEntity.ok("Password reset code sent to your email");
    }
    // Add a new endpoint for resetting password
    @GetMapping("/resetPassword")
    public User resetPassword(@RequestParam("email") String email,@RequestParam("password") String password) {
        return services.ResetPassword(email,password);

    }
    @GetMapping("getJuryCV/{id}")
    public String getJuryCV(@PathVariable("id") int idjurycv){
        return services.JuryCV(idjurycv);
    }
    @PostMapping("updateJuryCV/image/{id}")
    public JuryManager updateJuryCV(@PathVariable("id")int idJury, @RequestParam("image") MultipartFile image){
        return services.updateJuryCV(idJury,image);
    }
    @GetMapping("findjurybyid/{id}")
    public JuryManager findjurybyid(@PathVariable("id")int idjury){
        return juryRepo.findById(idjury).get();
    }
    @PostMapping("/payment")
    public PaymentInfo savePaymentInfo(@RequestBody PaymentInfo paymentInfo) {
        paymentInfo.setStartDate(LocalDateTime.now());
        return paymentInfoRepository.save(paymentInfo);
    }
    @PutMapping("/updateUserStatus/{userID}")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long userID, @RequestBody boolean status) {
        try {
            // Log the received status value
            System.out.println("Received status value for user with ID " + userID + ": " + status);

            // Update the user's status using the UserInfoService
            serviceUser.updateUserStatus(userID, status);
            return ResponseEntity.ok().body("User status updated successfully");
        } catch (IllegalArgumentException e) {
            // If user not found, return not found response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // If an unexpected error occurs, return internal server error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user status");
        }
    }
    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        User user = services.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    @PostMapping("/updateuser")
    public User updateUser (@RequestBody User user ){
        return services.updateUser(user);

    }
    @GetMapping("getUserCV/{id}")
    public String getUserCV(@PathVariable("id") int idUserCV) {
        return services.userCV(idUserCV);
    }
    @PostMapping("updateUserCV/image/{id}")
    public User updateUserCV(@PathVariable("id") Long idUser, @RequestParam("image") MultipartFile image) {
        return services.updateUserCV(idUser, image);
    }
    @GetMapping("findUserById/{id}")
    public User findUserById(@PathVariable("id") long idUser) {
        return userRepo.findById(idUser).orElse(null);
    }




@GetMapping("/countUsersByRole")
public ResponseEntity<?> countUsersByRole() {
    try {
        // Call a service method to count users by role
        Map<String, Long> roleCounts =  services.countUsersByRole();
        return ResponseEntity.ok().body(roleCounts);
    } catch (Exception e) {
        // If an error occurs, return an error response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error counting users by role");
    }}
    @GetMapping("/countUsersByStatus")
    public ResponseEntity<?> countUsersByStatus() {
        try {
            // Call the service method to count users by status
            Map<String, Long> statusCounts = services.countUsersByStatus();
            return ResponseEntity.ok().body(statusCounts);
        } catch (Exception e) {
            // If an error occurs, return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error counting users by status");
        }

    }
}








