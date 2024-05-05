package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.ENUM.DanceStyle;
import devnatic.danceodyssey.DAO.ENUM.ExperienceLevel;
import devnatic.danceodyssey.DAO.Entities.*;
import devnatic.danceodyssey.DAO.Repositories.CartRepository;
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
    private CartRepository cartRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



        Optional<User> userDetail = Optional.ofNullable(repository.findByEmail(username));
        System.err.println(username);

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }



    public static int generateRandomNumber() {
        Random rand = new Random();
        // Generate a random number between 10000000 and 99999999
        int randomNumber = rand.nextInt(90000000) + 10000000;
        return randomNumber;
    }
    public static DanceStyle getRandomDanceStyle() {
        DanceStyle[] danceStyles = DanceStyle.values();
        Random rand = new Random();
        int index = rand.nextInt(danceStyles.length);
        return danceStyles[index];
    }
    public static ExperienceLevel getRandomExperienceLevel() {
        ExperienceLevel[] experienceLevels = ExperienceLevel.values();
        Random rand = new Random();
        int index = rand.nextInt(experienceLevels.length);
        return experienceLevels[index];
    }


    public User addUser(User userInfo) {

        if (userInfo.getRole().getName().equals("Dancer")) {
            Dancer dancer = new Dancer();
            ExperienceLevel randomExperienceLevel = getRandomExperienceLevel();
            int randomNumber = generateRandomNumber();
            String randomString = Integer.toString(randomNumber);
            DanceStyle randomDanceStyle = getRandomDanceStyle();
            dancer.setExperienceLevel(randomExperienceLevel);
            dancer.setDanceStyle(randomDanceStyle);
            dancer.setTelNum(randomString);
            dancer.setFirstName(userInfo.getUsername());
            dancer.setLastName(userInfo.getLastName());
            dancer.setEmail(userInfo.getEmail());
            dancer.setPassword(userInfo.getPassword());

            dancerRepo.save(dancer);

            // Set the dancer ID as the user ID
           userInfo.setUserID ((long) dancer.getDancerId());
        } else if (userInfo.getRole().getName().equals("jury")) {
           JuryManager juryManager = new JuryManager();
            int randomNumber = generateRandomNumber();
            String randomString = Integer.toString(randomNumber);
            juryManager.setTelNumber(randomString);
            juryManager.setApproved(false);
            juryManager.setRejected(false);

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
        repository.save(userInfo);


        return userInfo;



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



