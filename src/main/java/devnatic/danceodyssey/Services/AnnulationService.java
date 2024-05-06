package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Annulation;
import devnatic.danceodyssey.DAO.Entities.DanceCourses;

import devnatic.danceodyssey.DAO.Repositories.AnnulationRepository;
import devnatic.danceodyssey.DAO.Repositories.DanceCoursesRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AnnulationService {

    @Autowired
    private AnnulationRepository annulationRepository;


    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private DanceCoursesRepository danceCoursesRepository;



    @Autowired
    public void DanceAnnulationService(JavaMailSender emailSender, DanceCoursesRepository danceCoursesRepository) {
        this.emailSender = emailSender;
        this.danceCoursesRepository = danceCoursesRepository;
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


    public ResponseEntity<Annulation> createAnnulation(Annulation annulation, int courseID) {
        Optional<DanceCourses> danceCourse = danceCoursesRepository.findById((long) courseID);
        if (danceCourse.isPresent()) {
            annulation.setDanceCourse(danceCourse.get());
            Annulation savedAnnulation = annulationRepository.save(annulation);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAnnulation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    public List<Annulation> getAllAnnulations() {
        return annulationRepository.findAll();
    }



}
