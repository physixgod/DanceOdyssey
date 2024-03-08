package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
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
    float price;
    Integer pointsPrice;
    String description;
    boolean productState;
    String Model;
    Integer quantity;
    Boolean archived ;
     LocalDate datePublication;
    @Lob
    @Column(name = "imageUrl", columnDefinition = "TEXT")
    String imageUrl;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private Set<ImageData> image;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<RatingProducts> ratingProductsP;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    CategoriesProduct categoriesProduct;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "subcategory_id", referencedColumnName = "idCategories")  // Spécifiez les deux colonnes
    private CategoriesProduct subCategoriesProduct;
    public void setCategoryProduct(CategoriesProduct category, boolean isSubCategory) {
        if (isSubCategory) {
            this.subCategoriesProduct = category;
        } else {
            this.categoriesProduct = category;
        }

        if (category != null) {
            category.getProductsSS_C().add(this);
        }
    }

    public void setCategoriesProduct(CategoriesProduct category) {
        setCategoryProduct(category, false);
    }

    public void setSubCategoriesProduct(CategoriesProduct subCategory) {
        setCategoryProduct(subCategory, true);
    }

}
