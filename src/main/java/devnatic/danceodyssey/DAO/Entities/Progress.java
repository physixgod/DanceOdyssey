package devnatic.danceodyssey.DAO.Entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String duration;
    private int difficulty;
    private int weight;
    private int nextGoal;
    private String comment;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Progress(String duration, int difficulty, int weight, int nextGoal, String comment) {
        this.duration = duration;
        this.difficulty = difficulty;
        this.weight = weight;
        this.nextGoal = nextGoal;
        this.comment = comment;
    }
}

