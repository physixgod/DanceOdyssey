package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DanceGroups")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int groupID;
    String groupName;
    String groupDescription;
    String danceStyle;
    String ageRange;
    String ageDiversity; // Diverse age representation
    boolean beginnerFriendly;
    boolean mentorshipProgram;
    @ManyToMany(mappedBy = "joinedGroups", cascade = CascadeType.ALL)
    @JsonIgnore()
    private Set<Dancer> dancers = new HashSet<>();
    //@ManyToMany(mappedBy = "joinedGroups", cascade = CascadeType.ALL)
    //private Set<Dancer> dancers;
}