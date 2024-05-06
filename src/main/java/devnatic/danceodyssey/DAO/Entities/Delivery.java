package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import devnatic.danceodyssey.DAO.ENUM.DeliveryDetails;
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
    @Enumerated(EnumType.STRING)
    DeliveryDetails deliveryDetails;
    LocalDate archive_date;
    boolean validated;
    @Enumerated(EnumType.STRING)
    DeliveryStatus deliveryStatus;
    String Adress;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="delivery")
    private Set<Orders> Orders;


    @ManyToMany(mappedBy="reclamationdelivery", cascade = CascadeType.ALL)
    private Set<Reclamation> reclamationsde;

}