package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Admin;
import devnatic.danceodyssey.DAO.Entities.Dancer;
import devnatic.danceodyssey.DAO.Entities.JuryManager;
import devnatic.danceodyssey.DAO.Entities.User;
import devnatic.danceodyssey.DAO.Repositories.AdminRepo;
import devnatic.danceodyssey.DAO.Repositories.DancerRepo;
import devnatic.danceodyssey.DAO.Repositories.JuryRepo;
import devnatic.danceodyssey.DAO.Repositories.UserRepo;
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
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService implements IUserServices {
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
    public String JuryCV(int idJuryCV) {
        JuryManager juryManager= juryRepo.findById(idJuryCV).get();
        String baseUrl = environment.getProperty("export.jury.images");
        String JuryCV = juryManager.getJuryCV();
        if (JuryCV != null && !JuryCV.isEmpty()) {


            return baseUrl + JuryCV;
        }

        return null;
    }

}
