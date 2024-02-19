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
public class Participate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idParticipate;
    int competitionRank;
    @ManyToOne(cascade = CascadeType.ALL)
    private Dancer DancerParticipated;
    @ManyToOne(cascade = CascadeType.ALL)
    private Competition competition;
}
