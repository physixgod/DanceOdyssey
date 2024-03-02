    package devnatic.danceodyssey.DAO.Entities;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.*;
    import lombok.experimental.FieldDefaults;

    import java.time.LocalDate;
    import java.util.List;
    import java.util.Set;

    @Entity
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class Competition {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        int CompetitionID;
        String competitionName;
        String danceCategory;
        String description;
        LocalDate startDate;
        LocalDate endDate;
        String location;
        int currentParticipants;
        int rating ;
        String prize;
        int maxParticipants;
        String status;
        @OneToMany(mappedBy = "competition")
        @JsonIgnore()
        private Set<Participate> participations;
        @ManyToMany(mappedBy="competitionsManagedByJuries", cascade = CascadeType.ALL)
        private Set<JuryManager> jurymanagers;

    }