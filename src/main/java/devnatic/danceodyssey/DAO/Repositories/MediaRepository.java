package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.MediaFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<MediaFiles,Integer> {
}
