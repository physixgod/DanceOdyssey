package devnatic.danceodyssey.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int userID;
    String firstName;
    String lastName;
    String email;
    String password;
    String telNumber;

    @OneToOne
    private CART cart;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<RatingProducts> RatingProductsS;




}
