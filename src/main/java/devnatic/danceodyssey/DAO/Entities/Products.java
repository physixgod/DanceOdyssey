package devnatic.danceodyssey.DAO.Entities;

import devnatic.danceodyssey.DAO.ENUM.Status;
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
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idProduct;
    int refProduct;
    String productName;
    float Price;
    int pointsPrice;
    String description;
    boolean ProductState;
    Status status;
    String Model;
    byte[] productimage;
    int quantity;
    @ManyToMany(mappedBy="reclamationproducts", cascade = CascadeType.ALL)
    private Set<Reclamation> reclamations;
}
