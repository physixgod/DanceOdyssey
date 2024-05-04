package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Dancer;
import devnatic.danceodyssey.DAO.Entities.JuryManager;
import devnatic.danceodyssey.DAO.Entities.User;
import devnatic.danceodyssey.DAO.Entities.UserInfoDetails;
import devnatic.danceodyssey.DAO.Repositories.DancerRepo;
import devnatic.danceodyssey.DAO.Repositories.JuryRepo;
import devnatic.danceodyssey.DAO.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserRepo repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private DancerRepo dancerRepo;
    @Autowired
    JuryRepo juryRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<User> userDetail = Optional.ofNullable(repository.findByEmail(username));
        System.err.println(username);

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }




    public User addUser(User userInfo) {
        if (userInfo.getRole().getName().equals("Dancer")) {
            Dancer dancer = new Dancer();

            dancer.setFirstName(userInfo.getUsername());
            dancer.setLastName(userInfo.getLastName());
            dancer.setEmail(userInfo.getEmail());
            dancer.setPassword(userInfo.getPassword());
            dancerRepo.save(dancer);

            // Set the dancer ID as the user ID
           userInfo.setUserID ((long) dancer.getDancerId());
        } else if (userInfo.getRole().getName().equals("jury")) {
           JuryManager juryManager = new JuryManager();

            juryManager.setFirstName(userInfo.getUsername());
            juryManager.setLastName(userInfo.getLastName());
            juryManager.setEmail(userInfo.getEmail());
            juryManager.setPassword(userInfo.getPassword());
            juryRepo.save(juryManager);
            userInfo.setUserID((long) juryManager.getJuryID());
            
        }
        else if (userInfo.getRole().getName().equals("User")) {
            Random random = new Random();
            int uniqueId = random.nextInt(10001); // Generates a random number between 0 and 10000
            userInfo.setUserID((long) uniqueId);

        }

        System.err.println(userInfo.getUserID());

        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        User user = repository.save(userInfo);
        return user;
        

    }

    public Long getLastAddedUserId() {
        // Sort by user ID in descending order to get the last added user
        Sort sort = Sort.by(Sort.Direction.DESC, "userID");

        // Find the first user in the sorted list (which will be the last added user)
        User lastAddedUser = repository.findAll(sort).stream().findFirst().orElse(null);

        // Return the user ID of the last added user
        return lastAddedUser != null ? lastAddedUser.getUserID() : null;
    }
    public void updateUserStatus(Long userID, boolean status) {
        Optional<User> userOptional = repository.findById(userID);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(status);
            repository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userID);
        }
    }

}



