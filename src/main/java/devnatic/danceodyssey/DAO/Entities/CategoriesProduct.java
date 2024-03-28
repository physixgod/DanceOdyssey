package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoriesProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idCategories;
    String type;


    public CategoriesProduct(String type) {
        this.type = type;
        subCatergorie = new ArrayList<>();
    }

    public List<CategoriesProduct> subCatergories() {
        return subCatergorie;
    }


    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "category_subcategory",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "subcategory_id")
    )

    @JsonIgnore
    private Set<CategoriesProduct> subcategories;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_is")
    private List<CategoriesProduct> subCatergorie = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriesProduct")
    @JsonIgnore
    private Set<Products> ProductsSS_C;
}