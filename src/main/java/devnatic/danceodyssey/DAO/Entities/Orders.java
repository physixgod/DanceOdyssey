package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import devnatic.danceodyssey.DAO.ENUM.Etat;
import devnatic.danceodyssey.DAO.ENUM.Payment_Mode;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Integer ordersId;
    String buyer_email;
    String buyer_address;
    Float totalPriceOders;
    LocalDate ordersDate;
    Long tax;
    String currency;
    Boolean archived ;
    @Enumerated(EnumType.STRING)
    private Payment_Mode payment_mode;
    @Enumerated(EnumType.STRING)
    private Etat etat;
    @ManyToOne
    @JsonIgnore
    Delivery delivery;

    @ManyToOne
    @JsonIgnore
    CART cartO;

}
