package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.Participate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipateRepository extends JpaRepository<Participate,Integer> {

    List<Participate> findByDancerParticipatedDancerId(int id);
    List<Participate> findByCompetitionCompetitionIDOrderByCompetitionRankAsc(int id);


}
