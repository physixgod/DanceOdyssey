/*package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.DanceCourses;
import devnatic.danceodyssey.Repositories.DanceCoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanceCoursesService {

    @Autowired
    private DanceCoursesRepository danceCoursesRepository;

    // Opérations CRUD

    public DanceCourses createDanceCourse(DanceCourses danceCourse) {
        return danceCoursesRepository.save(danceCourse);
    }

    public Iterable<DanceCourses> getAllDanceCourses() {
        return danceCoursesRepository.findAll();
    }



    public DanceCourses getDanceCourseById(int id) {
        return danceCoursesRepository.findById(id).orElse(null);
    }

    public DanceCourses updateDanceCourse(DanceCourses danceCourse) {
        return danceCoursesRepository.save(danceCourse);
    }

    public void deleteDanceCourseById(int id) {
        danceCoursesRepository.deleteById(id);
    }

    // Méthodes de recherche personnalisées (optionnel)

    public List<DanceCourses> findCoursesByCategory(String category) {
        return danceCoursesRepository.findByCategory(category);
    }

    public List<DanceCourses> findCoursesByName(String name) {
        return danceCoursesRepository.findByNameContaining(name);
    }

}*/
