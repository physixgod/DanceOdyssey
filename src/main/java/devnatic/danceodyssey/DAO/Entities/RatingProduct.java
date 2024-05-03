package devnatic.danceodyssey.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Integer id;
    @Column(name = "feedback") // Assurez-vous que le nom de la colonne est correct

    String feedBack;
    Integer score;


}