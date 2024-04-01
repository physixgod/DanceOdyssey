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
    int DancerId;
    String firstName;
    String lastName;
    String Password;
    int Points;
    @Enumerated(EnumType.STRING)
    DanceStyle danceStyle;
    @Enumerated(EnumType.STRING)
    ExperienceLevel experienceLevel;
    String Email;
    String TelNum;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Event> eventsAttendedByDancers;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Event> eventsCreatedByDancers;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Group> CreatedGroups;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Group> JoinedGroups;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<DanceCourses> dancecourses;
    @OneToMany(mappedBy = "DancerParticipated", cascade = CascadeType.ALL)
    private Set<Participate> participates;
    @OneToMany(mappedBy = "dancerRec", cascade = CascadeType.ALL)
    private Set<Reclamation> DancersReclamation;
    @OneToMany(mappedBy = "dancerfeed", cascade = CascadeType.ALL)
    private Set<Feedback> DancersFeedback;


}
