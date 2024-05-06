package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.DanceCourses;

import java.util.List;
import java.util.Map;

public interface IDanceCoursesServices {
    DanceCourses addDanceCourse(DanceCourses danceCourse);

    DanceCourses createDanceCourse(DanceCourses danceCourse);

    DanceCourses getDanceCourseById(Long id);

    DanceCourses updateDanceCourse(DanceCourses danceCourse);

    void deleteDanceCourseById(Long id);

    DanceCourses modifyDanceCourse(int courseID, DanceCourses courseDetails);


    public Map<String, Long> getCourseStatisticsByCategory() ;

    List<DanceCourses> findDanceCoursesByName(String name);

    List<DanceCourses> findDanceCoursesByStyle(String style);
    void updateAnnulationState(int courseID);


    void RefusAnnulationState(int courseID);

    List<DanceCourses> getAllCourses();
    // List<DanceCourses> findDanceCoursesByName(String name);
  //  List<DanceCourses> findDanceCoursesByStyle(String style);

}


