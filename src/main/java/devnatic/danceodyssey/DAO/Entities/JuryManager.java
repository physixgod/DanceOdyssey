package devnatic.danceodyssey.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JuryManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int juryID;
    String expertiseArea;
    String diploma;
    String firstName;
    String lastName;
    String email;
    String password;
    String telNumber;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Competition> competitionsManagedByJuries;
}
