package devnatic.danceodyssey.DAO.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthResponse {
     String token;
     Role role;
     String userName;
     String email;
     Long userID;
     boolean status;
     String  userCV;
}
