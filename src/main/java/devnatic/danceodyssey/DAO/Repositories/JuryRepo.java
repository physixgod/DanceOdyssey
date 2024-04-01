package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.JuryManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuryRepo extends JpaRepository<JuryManager,Integer> {
}
