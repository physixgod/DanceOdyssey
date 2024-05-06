package devnatic.danceodyssey.DAO.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Annulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int AnnulationID;
    String message;
    Boolean etat;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseID", nullable = false)
    @JsonBackReference
    DanceCourses danceCourse;

}
