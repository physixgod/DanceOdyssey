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
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int reclamationID;
    String reclamationDescription;
    String reclamationDate;
    String reclamationResponse;
    @ManyToOne
    User userRec; //corrected
    @ManyToOne
    Dancer dancerRec;
}
