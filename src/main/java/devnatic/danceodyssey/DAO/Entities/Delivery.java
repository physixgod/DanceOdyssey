package devnatic.danceodyssey.DAO.Entities;

import devnatic.danceodyssey.DAO.ENUM.DeliveryStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
    DeliveryStatus deliveryStatus;
    String Adress;
    float weight;


}
