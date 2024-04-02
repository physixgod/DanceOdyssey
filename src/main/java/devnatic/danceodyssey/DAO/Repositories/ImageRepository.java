package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.JuryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<JuryImage,Long> {
    List<JuryImage> findByJury_JuryID(Integer idProduct);

}
