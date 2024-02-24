package devnatic.danceodyssey.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DanceCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int courseID;
    String courseName;
    String description;
    String category;
    int durationInHours;
    String requiredSkillLevel;
    @ManyToMany(mappedBy="dancecourses", cascade = CascadeType.ALL)
    private Set<Dancer> dancers;
}
