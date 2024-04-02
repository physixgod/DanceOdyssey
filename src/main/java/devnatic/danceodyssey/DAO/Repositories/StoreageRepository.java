package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.JuryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreageRepository extends JpaRepository<JuryImage,Long> {
}
