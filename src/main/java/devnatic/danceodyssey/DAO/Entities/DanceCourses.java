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
    int CourseID;
    String CourseName;
    String Description;
    String Category;
    int DurationInHours;
    String requiredSkillLevel;
    @ManyToMany(mappedBy="dancecourses", cascade = CascadeType.ALL)
    private Set<Dancer> dancers;
}
