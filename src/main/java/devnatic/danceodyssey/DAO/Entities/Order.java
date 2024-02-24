package devnatic.danceodyssey.DAO.Entities;

import devnatic.danceodyssey.DAO.ENUM.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "CustomersOrders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int orderID;
    float totalPrice;
    LocalDate orderDate;
    PaymentMethod paymentMethod;
}
