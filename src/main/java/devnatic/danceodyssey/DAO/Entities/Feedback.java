package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;

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
    private boolean resolved;
    private String feedbackcomments;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    @ManyToOne
    User userfeed;
    @ManyToOne
    Dancer dancerfeed;
}
