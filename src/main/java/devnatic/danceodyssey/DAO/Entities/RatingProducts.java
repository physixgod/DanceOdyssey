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
public class RatingProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Integer id;
    String feedBack;
    Integer Score;


}
