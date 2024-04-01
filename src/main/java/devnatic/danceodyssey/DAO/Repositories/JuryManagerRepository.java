package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.JuryManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface JuryManagerRepository extends JpaRepository<JuryManager,Integer> {
    List<JuryManager> findByFirstNameContainingIgnoreCase(String name);
    List<JuryManager> findByExpertiseAreaContainingOrDiplomaContainingOrLastNameContainingOrFirstNameContainingOrEmailContaining(String expertiseArea, String diploma, String lastName, String firstName, String email);
}
