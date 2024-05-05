package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;


import java.util.Date;
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
    String reclamationResponse;
    String reclamationReason;
    String priority;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reclamationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private User userRec;
    @ManyToOne(cascade = CascadeType.ALL)
    private Dancer dancerRec;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Products> reclamationproducts;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Delivery> reclamationdelivery;


}
