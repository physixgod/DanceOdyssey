package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AccomodationRepository extends JpaRepository<Accommodation,Integer> {
    Set<Accommodation> findAccommodationsByAvailabelity(Boolean ava);
}
