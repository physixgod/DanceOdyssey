package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Admin;
import devnatic.danceodyssey.DAO.Entities.JuryManager;
import devnatic.danceodyssey.DAO.Entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IUserServices {
    User adduser(User user);
    Admin addadmin(Admin admin);
    public  void deleteUser(Long userID);
    List<User> getAllUsers();
    User loginUser(String email, String password);

    UserDetails loadUserByUsername(String email);

    User laodByEmail(String email);
    User getUserByUsername(String username);


    User ResetPassword(String Email, String password);
    public String JuryCV(int idJuryCV);
    public JuryManager updateJuryCV(int idJury, MultipartFile juryCV);
    public void closeSub();
    User getUserById(Long userId);
    public User updateUser(User user);

    public String  userCV(int idUsercv);

    public User updateUserCV(Long idUser, MultipartFile image);


    public Map<String, Long> countUsersByRole();

    Map<String, Long> countUsersByStatus();
}
