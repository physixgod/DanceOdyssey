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
    int JuryID;
    String expertiseArea;
    String diploma;
    String FirstName;
    String LastName;
    String Email;
    String Password;
    String TelNumber;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Competition> competitionsManagedByJuries;
}
