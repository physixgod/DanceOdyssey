package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepositories extends JpaRepository<Reclamation,Integer> {
    Reclamation findReclamationByReclamationID (Integer reclamationID);
}
