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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

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
            userInfo.setUserID((long) dancer.getDancerId());
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
            UUID uuid = UUID.randomUUID();
            long uniqueId = Math.abs(uuid.getMostSignificantBits()); // Get the most significant bits of the UUID
            userInfo.setUserID(uniqueId);
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

}


