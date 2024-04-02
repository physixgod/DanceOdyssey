package devnatic.danceodyssey.DAO.Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String username;
    private String password;
    private String lastName;
}
