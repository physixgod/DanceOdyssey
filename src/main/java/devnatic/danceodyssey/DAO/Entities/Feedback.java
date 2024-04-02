package devnatic.danceodyssey.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int feedbackID;

    private int rating;
    private String feedbackMessage;
    private String createdAt;
    private boolean resolved;

    @PrePersist
    public void prePersist() {
        // Set the current date when the entity is being persisted
        this.createdAt = LocalDate.now().toString();
    }
    @ManyToOne
    User userfeed;
    @ManyToOne
    Dancer dancerfeed;
}