package devnatic.danceodyssey.DAO.Entities;

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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int eventID;
    String eventName;
    LocalDate startDate;
    String location;
    int maxParticipants;
    @ManyToMany(mappedBy="eventsAttendedByUsers", cascade = CascadeType.ALL)
    private Set<User> users;
    @ManyToMany(mappedBy="eventsAttendedByDancers", cascade = CascadeType.ALL)
    private Set<Dancer> dancers;
    @ManyToMany(mappedBy="eventsCreatedByDancers", cascade = CascadeType.ALL)
    private Set<Dancer> eventsMakers;
}
