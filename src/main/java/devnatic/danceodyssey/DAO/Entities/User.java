package devnatic.danceodyssey.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {
    @Id

    Long userID;
    String userName;
    String lastName;
    String email;
    String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    String confpassword;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Event> eventsAttendedByUsers;
    @OneToMany(mappedBy = "userRec", cascade = CascadeType.ALL)
    private Set<Reclamation> userReclamations;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

