package devnatic.danceodyssey.DAO.Entities;

import devnatic.danceodyssey.DAO.ENUM.DeliveryStatus;
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
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idDelivery;
    LocalDate release_date;
    LocalDate archive_date;
    boolean validated;
    @Enumerated(EnumType.STRING)
    DeliveryStatus deliveryStatus;
    String Adress;
    float weight;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="delivery")
    private Set<Order> Orders;



}
