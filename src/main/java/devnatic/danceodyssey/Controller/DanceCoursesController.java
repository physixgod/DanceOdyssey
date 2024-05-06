package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.DAO.Entities.DanceCourses;
import devnatic.danceodyssey.Services.IDanceCoursesServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/course")
@CrossOrigin(origins = "*")
public class DanceCoursesController {

    @Autowired
    private IDanceCoursesServices danceCoursesService;










    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getCourseStatisticsByCategory() {
        Map<String, Long> statistics = danceCoursesService.getCourseStatisticsByCategory();
        return ResponseEntity.ok(statistics);
    }


    @PostMapping("/add")
    public ResponseEntity<DanceCourses> addDanceCourse(@RequestBody DanceCourses danceCourse) {
        DanceCourses addedCourse = danceCoursesService.addDanceCourse(danceCourse);
        return new ResponseEntity<>(addedCourse, HttpStatus.CREATED);
    }



    @GetMapping("/find/{id}")
    public ResponseEntity<DanceCourses> getDanceCourseById(@PathVariable Long id) {
        DanceCourses danceCourse = danceCoursesService.getDanceCourseById(id);
        if (danceCourse != null) {
            return new ResponseEntity<>(danceCourse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{courseID}")
    public DanceCourses updateDanceCourse(@PathVariable int courseID, @RequestBody DanceCourses courseDetails) {
        return danceCoursesService.modifyDanceCourse(courseID, courseDetails);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDanceCourseById(@PathVariable Long id) {
        danceCoursesService.deleteDanceCourseById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<DanceCourses>> findDanceCoursesByName(@PathVariable String name) {
        List<DanceCourses> courses = danceCoursesService.findDanceCoursesByName(name);
        if (!courses.isEmpty()) {
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByStyle/{style}")
    public ResponseEntity<List<DanceCourses>> findDanceCoursesByStyle(@PathVariable String style) {
        List<DanceCourses> courses = danceCoursesService.findDanceCoursesByStyle(style);
        if (!courses.isEmpty()) {
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 @GetMapping("/getAll")
    public List<DanceCourses> getAllCourses(){

     return danceCoursesService.getAllCourses();
 }


    @PutMapping("/update-annulation/{courseID}")
    public ResponseEntity<String> updateAnnulationState(@PathVariable int courseID) {
        danceCoursesService.updateAnnulationState(courseID);
        return ResponseEntity.ok("Annulation state updated successfully for course ID: " + courseID);
    }



    @PutMapping("/refus-annulation/{courseID}")
    public ResponseEntity<String> RefusAnnulationState(@PathVariable int courseID) {
        danceCoursesService.RefusAnnulationState(courseID);
        return ResponseEntity.ok("Annulation state updated successfully for course ID: " + courseID);
    }




}
