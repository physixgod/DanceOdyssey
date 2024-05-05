package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import devnatic.danceodyssey.Services.JuryService;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JuryManager {
    //private static final Logger log = LoggerFactory.getLogger(JuryService.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int juryID;
    // Add the archived field
    boolean archived;
    boolean approved = false;
    boolean rejected = false;
    String expertiseArea;
    String diploma;
    String firstName;
    String lastName;
    String email;
    String password;
    String telNumber;
    String juryCV;
    @Lob
    @Column(name = "imageUrl", columnDefinition = "TEXT")
    String imageUrl;
    @OneToMany(mappedBy = "jury", cascade = CascadeType.ALL)
    private Set<JuryImage> jury_data;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore()
    private Set<Competition> competitionsManagedByJuries;


}

