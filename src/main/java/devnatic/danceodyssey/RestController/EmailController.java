package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.EmailSender.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins =  "http://localhost:4200/,http://localhost:8086")
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailServices emailServices;

    @GetMapping("/send")
    public String sendEmail(@RequestParam String to,
                            @RequestParam String subject,
                            @RequestParam String body) {
        try {
            emailServices.sendEmail(to, subject, body);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
}