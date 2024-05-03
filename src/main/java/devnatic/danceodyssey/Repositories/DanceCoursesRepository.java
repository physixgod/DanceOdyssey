/* package devnatic.danceodyssey.Repositories;


import devnatic.danceodyssey.DAO.Entities.DanceCourses;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanceCoursesRepository extends CrudRepository<DanceCourses, Integer> {

       List<DanceCourses> findByCategory(String category);

    List<DanceCourses> findByNameContaining(String name);

}*/