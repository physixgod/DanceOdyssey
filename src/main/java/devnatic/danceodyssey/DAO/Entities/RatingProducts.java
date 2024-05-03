package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class RatingProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Integer id;
    @Column(name = "feedback") // Assurez-vous que le nom de la colonne est correct

    String feedBack;
    Integer score;


}
