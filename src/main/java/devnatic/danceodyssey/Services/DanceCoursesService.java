package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.ENUM.Category;
import devnatic.danceodyssey.DAO.Entities.Annulation;
import devnatic.danceodyssey.DAO.Entities.DanceCourses;
import devnatic.danceodyssey.DAO.Repositories.DanceCoursesRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class DanceCoursesService implements IDanceCoursesServices{

    @Autowired
    private DanceCoursesRepository danceCoursesRepository;


    @Autowired
    private JavaMailSender emailSender;



    @Autowired
    public DanceCoursesService(JavaMailSender emailSender, DanceCoursesRepository danceCoursesRepository) {
        this.emailSender = emailSender;
        this.danceCoursesRepository = danceCoursesRepository;
    }

    public DanceCourses addDanceCourse(DanceCourses danceCourse) {
        DanceCourses addedCourse = danceCoursesRepository.save(danceCourse);
        sendEmail(danceCourse);
        return addedCourse;
    }
    private void sendEmailAnnilationRefus(DanceCourses danceCourse) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom("oumaymaakermi3@gmail.com");
            helper.setTo("oumaymaakermi9999@gmail.com");
            helper.setSubject("refus  demande coursDances  ");


            String content = "Bonjour,<br><br>" +
                    "Votre demande a été refusée . Nous ne pouvons pas accepter votre raison " ;




            helper.setText(content, true); // true pour indiquer que le contenu est HTML

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendEmailAnnilation(DanceCourses danceCourse) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom("oumaymaakermi3@gmail.com");
            helper.setTo("oumaymaakermi9999@gmail.com");
            helper.setSubject("acceptation Demande coursDances  ");


            String content = "Bonjour,<br><br>" +
                    "Votre demande a été traitée avec succès. Nous acceptons votre raison. " ;




            helper.setText(content, true); // true pour indiquer que le contenu est HTML

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }





    private void sendEmail(DanceCourses danceCourse) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom("oumaymaakermi3@gmail.com");
            helper.setTo("oumaymaakermi9999@gmail.com");
            helper.setSubject("Nouveau cours de danse ajouté");


            String content = "Bonjour,<br><br>" +
                    "Nous tenons à vous rappeler que vous devez confirmer votre inscription au cours de " +
                    danceCourse.getCourseName() + " au moins une semaine avant la date de début du cours, soit le : " +
                    danceCourse.getDateCourse() + ".<br><br>" +
                    "Cordialement,<br>" +
                    "L'équipe de Dance Odyssey";




            helper.setText(content, true); // true pour indiquer que le contenu est HTML

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }




    @Override
    public DanceCourses modifyDanceCourse(int courseID, DanceCourses courseDetails) {
        Optional<DanceCourses> courseOptional = danceCoursesRepository.findById((long) courseID);
        if(courseOptional.isPresent()) {
            DanceCourses course = courseOptional.get();
            course.setCourseName(courseDetails.getCourseName());
            course.setDescription(courseDetails.getDescription());
            course.setCategory(courseDetails.getCategory());
            course.setDurationInHours(courseDetails.getDurationInHours());
            course.setRequiredSkillLevel(courseDetails.getRequiredSkillLevel());
            course.setDateCourse(courseDetails.getDateCourse());
            course.setVideoUrl(courseDetails.getVideoUrl());
            return danceCoursesRepository.save(course);
        } else {
            throw new RuntimeException("Cours non trouvé avec l'ID : " + courseID);
        }
    }





    @Override
    public DanceCourses createDanceCourse(DanceCourses danceCourse) {
        return null;
    }
    @Override
    public DanceCourses getDanceCourseById(Long id) {
        Optional<DanceCourses> optionalDanceCourse = danceCoursesRepository.findById(id);
        return optionalDanceCourse.orElse(null);
    }
    @Override
    public DanceCourses updateDanceCourse(DanceCourses danceCourse) {

        DanceCourses existingCourse = danceCoursesRepository.findById((long) danceCourse.getCourseID()).orElse(null);
        if (existingCourse != null) {

            existingCourse.setCourseName(danceCourse.getCourseName());
            existingCourse.setDescription(danceCourse.getDescription());
            existingCourse.setCategory(danceCourse.getCategory());
            existingCourse.setDurationInHours(danceCourse.getDurationInHours());
            existingCourse.setRequiredSkillLevel(danceCourse.getRequiredSkillLevel());
            return danceCoursesRepository.save(existingCourse);
        }
        return null;
    }
    @Override
    public void deleteDanceCourseById(Long id) {
        danceCoursesRepository.deleteById(id);
    }

    @Override
    public List<DanceCourses> findDanceCoursesByName(String name) {
        return danceCoursesRepository.findByCourseName(name);
    }

    @Override
    public List<DanceCourses> findDanceCoursesByStyle(String style) {
        return danceCoursesRepository.findByCategory(style);
    }

    @Transactional
    public List<DanceCourses> getAllCourses() {
        return danceCoursesRepository.findAll();
    }





    @Override
    public void updateAnnulationState(int courseID) {
        Optional<DanceCourses> optionalCourse = danceCoursesRepository.findById((long) courseID);
        if (optionalCourse.isPresent()) {
            DanceCourses course = optionalCourse.get();
            List<Annulation> annulations = course.getAnnulations();
            for (Annulation annulation : annulations) {
                annulation.setEtat(true);
            }
            danceCoursesRepository.save(course);
           sendEmailAnnilation(course);
        } else {
            throw new EntityNotFoundException("Course not found with ID: " + courseID);
        }
    }


    @Override
    public void RefusAnnulationState(int courseID) {
        Optional<DanceCourses> optionalCourse = danceCoursesRepository.findById((long) courseID);
        if (optionalCourse.isPresent()) {
            DanceCourses course = optionalCourse.get();
            List<Annulation> annulations = course.getAnnulations();

            for (Annulation annulation : annulations) {
                annulation.setEtat(false);
            }

            danceCoursesRepository.save(course);

        sendEmailAnnilationRefus(course);
        } else {
            throw new EntityNotFoundException("Cours non trouvé avec l'ID : " + courseID);
        }
    }





    public Map<String, Long> getCourseStatisticsByCategory() {
        List<Object[]> results = danceCoursesRepository.countCoursesByCategory();
        return results.stream()
                .collect(Collectors.toMap(row -> ((Category) row[0]).name(), row -> (Long) row[1]));
    }


}