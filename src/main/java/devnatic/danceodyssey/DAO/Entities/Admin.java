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
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idAdmin;
    String username;
    String password;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Competition> Competitions;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Products> ProductsSSA;
}