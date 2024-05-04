package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idAcc;
    double pricePerNight;
    int availableSlots;
    String location;
    String contact;
    Boolean availabelity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Dancer hoster;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Event eventAccom;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Dancer> residents;
    Boolean breakfeast;
    Boolean lunch;
    Boolean dinner;
    double breakfeastPrice;
    double lunchPrice;
    double dinnerPrice;
}
