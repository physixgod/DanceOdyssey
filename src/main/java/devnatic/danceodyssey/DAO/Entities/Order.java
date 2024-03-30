package devnatic.danceodyssey.DAO.Entities;

import devnatic.danceodyssey.DAO.ENUM.Payment_mode;
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
    @Enumerated(EnumType.STRING)
    Payment_mode payment_mode;
    @ManyToOne(cascade = CascadeType.ALL)
    Products products;
    @ManyToOne
    Delivery delivery;
    @ManyToOne
    CART carT;


}
