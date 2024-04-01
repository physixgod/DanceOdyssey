package devnatic.danceodyssey.DAO.Entities;

import devnatic.danceodyssey.DAO.ENUM.Category;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
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
    @Enumerated(EnumType.STRING)
    Category category;
    int durationInHours;
    String requiredSkillLevel;
    String dateCourse;
    String videoUrl;

    @ManyToMany(mappedBy = "dancecourses", cascade = CascadeType.ALL)
    private Set<Dancer> dancers;
}