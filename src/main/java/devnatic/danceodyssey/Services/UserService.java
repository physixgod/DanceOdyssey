package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Admin;
import devnatic.danceodyssey.DAO.Entities.User;
import devnatic.danceodyssey.DAO.Entities.UserInfoDetails;
import devnatic.danceodyssey.DAO.Repositories.AdminRepo;
import devnatic.danceodyssey.DAO.Repositories.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService implements IUserServices {
@Autowired
    private UserRepo userRepo;

    private AdminRepo adminRepo;

    @Override
    public User adduser(User user) {

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
    }}
