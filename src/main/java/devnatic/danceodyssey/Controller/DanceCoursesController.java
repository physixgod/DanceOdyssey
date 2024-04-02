package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.DAO.Entities.DanceCourses;
import devnatic.danceodyssey.Services.DanceCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dancecourses")
public class DanceCoursesController {

    @Autowired
    private DanceCoursesService danceCoursesService;

    // API pour les opérations CRUD

    @PostMapping
    public DanceCourses createDanceCourse(@RequestBody DanceCourses danceCourse) {
        return danceCoursesService.createDanceCourse(danceCourse);
    }

    @GetMapping
    public Iterable<DanceCourses> getAllDanceCourses() {
        return danceCoursesService.getAllDanceCourses();
    }


    @GetMapping("/{id}")
    public DanceCourses getDanceCourseById(@PathVariable int id) {
        return danceCoursesService.getDanceCourseById(id);
    }

    @PutMapping("/{id}")
    public DanceCourses updateDanceCourse(@PathVariable int id, @RequestBody DanceCourses danceCourse) {
        return danceCoursesService.updateDanceCourse(danceCourse);
    }

    @DeleteMapping("/{id}")
    public void deleteDanceCourseById(@PathVariable int id) {
        danceCoursesService.deleteDanceCourseById(id);
    }

    // API pour les recherches personnalisées (optionnel)

    @GetMapping("/category/{category}")
    public List<DanceCourses> findCoursesByCategory(@PathVariable String category) {
        return danceCoursesService.findCoursesByCategory(category);
    }

    @GetMapping("/name/{name}")
    public List<DanceCourses> findCoursesByName(@PathVariable String name) {
        return danceCoursesService.findCoursesByName(name);
    }

}
