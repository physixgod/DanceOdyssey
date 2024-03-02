package devnatic.danceodyssey.DAO.Entities;

import devnatic.danceodyssey.DAO.ENUM.DanceStyle;
import devnatic.danceodyssey.DAO.ENUM.ExperienceLevel;
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
public class Dancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int dancerId;
    String firstName;
    String lastName;
    String Password;
    int points;
    @Enumerated(EnumType.STRING)
    DanceStyle danceStyle;
    @Enumerated(EnumType.STRING)
    ExperienceLevel experienceLevel;
    String email;
    String telNum;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Event> eventsAttendedByDancers;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Event> eventsCreatedByDancers;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Group> createdGroups;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Group> joinedGroups;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<DanceCourses> dancecourses;
    @OneToMany(mappedBy = "DancerParticipated", cascade = CascadeType.ALL) // corrected
    private Set<Participate> participates;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="dancerRec")
    private Set<Reclamation> dancersReclamations;

}
