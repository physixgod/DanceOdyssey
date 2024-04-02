package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition,Integer> { 
    List<Competition> findCompetitionsByCompetitionNameContainingIgnoreCase(String CompetitionName);

List<Competition> findCompetitionByLocationIsContainingIgnoreCase(String Location);
List<Competition> findCompetitionByStartDateBefore(LocalDate date);
List<Competition> findCompetitionByDanceCategoryContainingIgnoreCase(String dance);
List<Competition> findByStatus(String status);
List<Competition> findCompetitionsByStartDateIsBefore(LocalDate datenow);


}
