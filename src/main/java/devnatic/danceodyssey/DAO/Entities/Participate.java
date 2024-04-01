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
@ToString
public class Participate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idParticipate;
    int competitionRank;
    double competitionScore;
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude

    private Dancer dancerParticipated;

    @ManyToOne(cascade = CascadeType.ALL)
    private Competition competition;
}