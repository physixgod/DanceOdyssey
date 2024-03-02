package devnatic.danceodyssey.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "DanceGroups")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int groupID;
    String groupName;
    String groupDescription;

}
