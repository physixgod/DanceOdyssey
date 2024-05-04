package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.DAO.Entities.Feedback;
import devnatic.danceodyssey.DAO.Entities.Reclamation;
import devnatic.danceodyssey.Services.IFeedbackservices;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/feedback")
public class FeedbackController {

    private final IFeedbackservices feedbackServices;

    @PostMapping("/Addfeedback")
    public ResponseEntity<Feedback> Addfeedback(@RequestBody Feedback feedback){
        Feedback newfeedback =feedbackServices.AddFeedback(feedback);
        return new ResponseEntity<>(newfeedback,HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public Feedback updateFeedback(
            @PathVariable("id") int feedbackId,
            @RequestParam("resolved") boolean resolved) {
        return feedbackServices.Updatefeedback(feedbackId, resolved);
    }

    @GetMapping("/showall")
    public ResponseEntity<List<Feedback>> showFeedback() {
        List<Feedback> feedbacks = feedbackServices.Showfeedback();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Feedback> GetFeedbackId(@PathVariable("id") int id){
        Feedback feedback = feedbackServices.FindfeedbackbyID(id);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

}
