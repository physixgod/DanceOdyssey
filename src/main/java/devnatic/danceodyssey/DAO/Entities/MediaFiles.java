package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String imageUrl;

    @Transient
    MultipartFile image;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Products product;

}
