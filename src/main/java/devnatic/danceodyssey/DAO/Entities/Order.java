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
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer orderId;
    Float totalPrice;
    LocalDate orderDate;
     Boolean archive;
     String description;

    @ManyToOne(cascade = CascadeType.ALL)
    Products products;
    @ManyToOne
    Delivery delivery;
    @ManyToOne
    CART carT;


}
