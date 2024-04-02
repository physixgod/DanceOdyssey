package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int competitionID;
    String competitionName;
    String danceCategory;
    String description;
    LocalDate startDate;
    LocalDate endDate;
    String location;
    int currentParticipants;
    int rating ;
    String prize;
    int maxParticipants;
    String status;
    String competitionImage;
    String excelFile;
    @OneToMany(mappedBy = "competition")
    @JsonIgnore()
    @ToString.Exclude
    private Set<Participate> participations;
    @ToString.Exclude
    @ManyToMany(mappedBy="competitionsManagedByJuries", cascade = CascadeType.ALL)
    private Set<JuryManager> jurymanagers;
}