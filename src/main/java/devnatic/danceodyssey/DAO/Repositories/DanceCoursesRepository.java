package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.DanceCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DanceCoursesRepository extends JpaRepository<DanceCourses, Long> {
    List<DanceCourses> findByCourseName(String name);

    List<DanceCourses> findByCategory(String style);


  //  List<DanceCourses> findById(int courseID);  // Assuming you're fetching by ID



    @Query("SELECT c.category, COUNT(c) FROM DanceCourses c GROUP BY c.category")
    List<Object[]> countCoursesByCategory();


}

