package devnatic.danceodyssey.DAO.Entities;
<<<<<<< HEAD

=======
>>>>>>> 695318ec7c342aef606be4a6434ae47bf61ac053
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString
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
    @JsonIgnore()
    private Set<Event> eventsCreatedByDancers;

    //@ManyToMany(mappedBy = "dancers", cascade = CascadeType.ALL)
    //private Set<Group> joinedGroups;// corrected
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore()
    private Set<Group> joinedGroups;
    @JsonIgnore()
    @ManyToMany(cascade = CascadeType.ALL)

    private Set<DanceCourses> dancecourses;
<<<<<<< HEAD
    @JsonIgnore()
    @OneToMany(mappedBy = "DancerParticipated", cascade = CascadeType.ALL) // corrected
=======
    @OneToMany(mappedBy = "dancerParticipated", cascade = CascadeType.ALL) // corrected
    @JsonIgnore()
>>>>>>> 695318ec7c342aef606be4a6434ae47bf61ac053
    private Set<Participate> participates;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="dancerRec")
    @JsonIgnore()
    private Set<Reclamation> dancersReclamations;
<<<<<<< HEAD
}
=======

}
>>>>>>> 695318ec7c342aef606be4a6434ae47bf61ac053
