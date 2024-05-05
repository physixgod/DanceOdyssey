package devnatic.danceodyssey.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int reclamationID;
    String reclamationDescription;
    String reclamationDate;
    String reclamationResponse;
    String reclamationReason;
    String imageurl;
    @ManyToOne(cascade = CascadeType.ALL)
    private User userRec;
    @ManyToOne(cascade = CascadeType.ALL)
    private Dancer dancerRec;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Products> reclamationproducts;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Delivery> reclamationdelivery;

    @PrePersist
    public void prePersist() {
        // Set the current date when the entity is being persisted
        this.reclamationDate = LocalDate.now().toString();
    }
}
