package devnatic.danceodyssey.DAO.Entities;

import devnatic.danceodyssey.DAO.ENUM.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    float price;
    int pointsPrice;
    String description;
    boolean productState;
    Status status;
    String model;
    byte[] productimage;
    int quantity;
}
