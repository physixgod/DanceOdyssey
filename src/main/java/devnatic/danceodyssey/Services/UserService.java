package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.*;
import devnatic.danceodyssey.DAO.Repositories.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService implements IUserServices {
    @Autowired
    PaymentInfoRepository paymentInfoRepository;
@Autowired
    private UserRepo userRepo;
@Autowired
   private Environment environment;
    @Autowired
     private NameFile nameFile;
    @Autowired
    private FileUtil util;
    private AdminRepo adminRepo;
    private DancerRepo dancerRepo;
    @Autowired
    private JuryRepo juryRepo;
    private final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User adduser(User user) {
        if (user.getRole().getName().equals("Dancer")){
            Dancer dancer= new Dancer();
            dancer.setFirstName(user.getUsername());
            dancer.setLastName(user.getLastName());
            dancer.setEmail(user.getEmail());
            dancer.setPassword(user.getPassword());
            dancerRepo.save(dancer);
        }
        System.err.println(user.getRole().getId());
        return userRepo.save(user);

    }

    @Override
    public Admin addadmin(Admin admin) {
        return  adminRepo.save(admin);
    }

    @Override
    public void deleteUser(Long userID) {
        userRepo.deleteById(userID);

    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User loginUser(String email, String password) {

        User user = userRepo.findByEmailLike(email);
        System.err.println(userRepo);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Retrieve user details from the repository based on the email
        User user = userRepo.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Convert User entity to UserDetails
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail()) // Use email as username
                .password(user.getPassword())
                .authorities(Collections.emptyList()) // You can set authorities if needed
                .build();
    }

    @Override
    public User laodByEmail(String email) throws UsernameNotFoundException {
        // Retrieve user details from the repository based on the email
        User user = userRepo.findByEmail(email);
        // Convert User entity to UserDetails
        return user;
    }

    @Override
    public User getUserByUsername(String userName) {
        return userRepo.findByUserName(userName);
    }
    @Override
    public User ResetPassword(String Email, String password){
        User user = userRepo.findByEmail(Email);
        user.setPassword(passwordEncoder.encode(password));
        user.setConfpassword(password);
        return userRepo.save(user);

    }
    public JuryManager updateJuryCV(int idJury, MultipartFile juryCV) {
        JuryManager juryManager= juryRepo.findById(idJury).get();
        try {
            if (juryCV != null && !juryCV.isEmpty() && juryCV.getSize() > 0) {
                String uploadDir = environment.getProperty("upload.jury.images");

                String newPhotoName = nameFile.nameFile(juryCV);
                juryManager.setJuryCV(newPhotoName);



                util.saveNewFile(uploadDir, newPhotoName, juryCV);
            }
            return juryRepo.save(juryManager);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update competition photo", e);
        }
    }

    @Override
    public void closeSub() {
        List<PaymentInfo> paymentInfos=paymentInfoRepository.findAll();
        for (PaymentInfo paymentInfo:paymentInfos){
            if (Objects.equals(paymentInfo.getSubtype(), "1 Year Subscription")){
                long yearsPassed = ChronoUnit.HOURS.between(paymentInfo.getStartDate(), LocalDateTime.now());
                if (yearsPassed>=30){
                    User user=userRepo.findById(paymentInfo.getId()).get();
                    user.setStatus(false);
                    userRepo.save(user);
                }
            }
            if (Objects.equals(paymentInfo.getSubtype(), "6 Months Subscription")){
                long monthpassed = ChronoUnit.SECONDS.between(paymentInfo.getStartDate(), LocalDateTime.now());
                if (monthpassed>=10){
                    User user=userRepo.findById(paymentInfo.getId()).get();
                    user.setStatus(false);
                    userRepo.save(user);
                }
            }

        if (Objects.equals(paymentInfo.getSubtype(), "1 Month Subscription")){
            long minutes = ChronoUnit.MINUTES.between(paymentInfo.getStartDate(), LocalDateTime.now());
            System.err.println(paymentInfo.getStartDate());
            System.err.println(LocalDateTime.now());
            if (minutes>=1){
                User user=userRepo.findById(paymentInfo.getId()).get();
                user.setStatus(false);
                userRepo.save(user);
            }
        }
    }
    }

    @Override
    public User getUserById(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }

    @Override
    public User updateUser(User userInfo) {

        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        User user = userRepo.save(userInfo);

        return userRepo.save(user);
    }

    @Override
    public String JuryCV(int idJuryCV) {
        JuryManager juryManager= juryRepo.findById(idJuryCV).get();
        String baseUrl = environment.getProperty("export.jury.images");
        String JuryCV = juryManager.getJuryCV();
        if (JuryCV != null && !JuryCV.isEmpty()) {
            System.err.println(baseUrl + JuryCV);
            return baseUrl + JuryCV;
        }

        return null;
    }


    @Override
    public String  userCV(int idUsercv) {
       User user= userRepo.findById((long) idUsercv).get();
        String baseUrl = environment.getProperty("export.jury.images");
        String userCV = user.getUserCV();
        if (userCV != null && !userCV.isEmpty()) {
            System.err.println(baseUrl + userCV);
            return baseUrl + userCV;
        }

        return null;
    }



    @Override

    public User updateUserCV(Long idUser, MultipartFile userCV) {
        User user = userRepo.findById(idUser).get();
        try {
            if (userCV != null && !userCV.isEmpty() && userCV.getSize() > 0) {
                String uploadDir = environment.getProperty("upload.jury.images");

                String newPhotoName = nameFile.nameFile(userCV);
                user.setUserCV(newPhotoName);


                util.saveNewFile(uploadDir, newPhotoName, userCV);
            }
            return userRepo.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update competition photo", e);
        }
    }

    @Override
    public Map<String, Long> countUsersByRole() {
        Map<String, Long> roleCounts = new HashMap<>();
        List<User> users = userRepo.findAll();

        for (User user : users) {
            String roleId = user.getRole().getId().toString();
            roleCounts.put(roleId, roleCounts.getOrDefault(roleId, 0L) + 1);
        }

        return roleCounts;
    }
    @Override
    public Map<String, Long> countUsersByStatus() {
        Map<String, Long> statusCounts = new HashMap<>();
        List<User> users = userRepo.findAll();

        for (User user : users) {
            String status = user.getStatus() ? "1" : "0"; // Convert boolean status to string "1" or "0"
            statusCounts.put(status, statusCounts.getOrDefault(status, 0L) + 1);
        }

        return statusCounts;
    }

}
