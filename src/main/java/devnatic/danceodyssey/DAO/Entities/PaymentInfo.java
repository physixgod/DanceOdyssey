package devnatic.danceodyssey.DAO.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;



@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentInfo {
    @Id

    private Long id;

    private String nameOnCard;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private String subtype;

    // Getters and setters
}