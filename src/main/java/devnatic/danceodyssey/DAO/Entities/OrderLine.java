package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Integer orderId;
    Float totalPrice;
    LocalDate orderDate;
    String description;
    Integer nbProdO;
    Float detailPrice;
    String imgUrl;
    @ManyToOne
    @JsonIgnore
    CART cart;
    @ManyToOne
    @JsonIgnore
    Products products;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "orderLineId", referencedColumnName = "ordersId")
    Orders orders;
}
