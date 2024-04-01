package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idProduct;
    Integer refProduct;
    String productName;
    Float price;
    Integer pointsPrice;
    String description;
    Boolean productState;
    String Model;
    Integer quantity;
    Boolean archived ;
    LocalDate datePublication;
    Boolean isPromotion;
    Float pricePromotion;
    Integer pourcentagePromotion;
    Integer quantiteVendue;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Image> images;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<RatingProducts> ratingProductsP;
    // Relation avec ParentCategory
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parent_category_id")
    ParentCategory parentCategory;


}


