package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Admin;
import devnatic.danceodyssey.DAO.Entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IUserServices {
    User adduser(User user);
    Admin addadmin(Admin admin);
    public  void deleteUser(Long userID);
    List<User> getAllUsers();
    User loginUser(String email, String password);

    UserDetails loadUserByUsername(String email);

    User laodByEmail(String email);
    User getUserByUsername(String username);



}
