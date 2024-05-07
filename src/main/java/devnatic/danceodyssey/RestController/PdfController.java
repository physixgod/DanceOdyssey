package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.DAO.Entities.Feedback;
import devnatic.danceodyssey.DAO.Repositories.FeedbackRepositories;
import devnatic.danceodyssey.Services.PdfService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class PdfController {

    @Autowired
    FeedbackRepositories feedbackRepositories; // Assuming you have a repository for Feedback entity

    @Autowired
    private PdfService pdfService;

    @GetMapping("/export/feedback/pdf")
    public ResponseEntity<byte[]> exportFeedbackToPdf() {
        try {
            List<Feedback> feedbackList = feedbackRepositories.findAll(); // Fetch feedback from database
            byte[] pdfBytes = pdfService.generateFeedbackPdf(feedbackList);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "feedback_report.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
