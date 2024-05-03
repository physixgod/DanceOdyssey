package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String type;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    ParentCategory parentCategory;
    @OneToMany(mappedBy = "subCategories", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Products> products;
}