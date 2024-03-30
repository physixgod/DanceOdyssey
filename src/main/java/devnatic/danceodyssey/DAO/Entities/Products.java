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
    Float price;
    Integer pointsPrice;
    String description;
    Boolean productState;
    String Model;
    Integer quantity;
    Boolean archived ;
    LocalDate datePublication;
    Boolean isPromotion;
    Float prixPromotion;
    Integer pourcentagePromotion;
    Boolean isFlashSale;
    Integer quantiteVendue;
    // Méthode pour mettre à jour productState
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        if (quantity != null && quantity.equals(0)) {
            this.productState = true; // Si la quantité est égale à 0, le productState est mis à true
        }
    }
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private Set<Image> images;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<RatingProducts> ratingProductsP;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    CategoriesProduct categoriesProduct;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "subcategory_id", referencedColumnName = "idCategories")
    private CategoriesProduct subCategoriesProduct;
    public void setCategoryProduct(CategoriesProduct category, Boolean isSubCategory) {
        if (isSubCategory) {
            this.subCategoriesProduct = category;
        } else {
            this.categoriesProduct = category;
        }

        if (category != null) {
            category.getProductsSS_C().add(this);
        }

    }

    public Set<Image> getImages() {
        return images;
    }
    public void setCategoriesProduct(CategoriesProduct category) {
        setCategoryProduct(category, false);
    }

    public void setSubCategoriesProduct(CategoriesProduct subCategory) {
        setCategoryProduct(subCategory, true);
    }






}


