package devnatic.danceodyssey.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
        int currentParticipants;
    String eventName;
    LocalDate startDate;
    String location;
    int maxParticipants;
    Boolean cancelled;
    String description;
    private double latitude;
    private double longitude;
    String eventImage;
    @ManyToMany(mappedBy="eventsAttendedByUsers", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<User> users;
    @ManyToMany(mappedBy="eventsAttendedByDancers", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Dancer> dancers;
    @ManyToMany(mappedBy="eventsCreatedByDancers", cascade = CascadeType.ALL)
    @JsonIgnore()
    private Set<Dancer> eventsMakers;
}
