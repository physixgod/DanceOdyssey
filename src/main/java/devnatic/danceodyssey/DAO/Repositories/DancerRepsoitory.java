package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.Dancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DancerRepsoitory extends JpaRepository<Dancer,Integer> {
    Dancer findDancerByFirstNameAndLastName(String firstName,String LastName);


}
