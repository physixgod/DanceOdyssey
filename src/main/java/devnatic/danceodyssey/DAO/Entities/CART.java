package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import devnatic.danceodyssey.DAO.ENUM.Etat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CART {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Float totPrice;
Integer TotalProducts;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private List<OrderLine> orderList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cartO")
    private List<Orders> ordersList = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private User user ;
}
