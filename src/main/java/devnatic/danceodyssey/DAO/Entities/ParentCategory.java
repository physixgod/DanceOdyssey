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
public class ParentCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String type;
    @Column
    String imgUrl;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    List<SubCategory> subCategories;
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    @JsonIgnore
     List<Products> products;
}
