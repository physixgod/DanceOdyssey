package devnatic.danceodyssey.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int CompetitionID;
    String competitionName;
    String danceCategory;
    LocalDate startDate;
    LocalDate endDate;
    String location;
    int rating ;
    String prize;
    int maxParticipants;
    String status;
    String excelFile;
    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
    private Set<Participate> participations;
    @ManyToMany(mappedBy="competitionsManagedByJuries", cascade = CascadeType.ALL)
    private Set<JuryManager> jurymanagers;

}
