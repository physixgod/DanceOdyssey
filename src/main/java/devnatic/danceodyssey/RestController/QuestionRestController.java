package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.DAO.Entities.Question;
import devnatic.danceodyssey.DAO.Repositories.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RequestMapping("/question")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionRestController {
    private QuestionRepository questionRepository;
    @GetMapping("/getRandomQuestion")
    public Set<Question> getRandomTenQuestions() {
        List<Question> allQuestions = questionRepository.findAll();
        Set<Question> output=new HashSet<>();
        for(int i=0;i<10;i++){
            int randomIndex = new Random().nextInt(allQuestions.size());
            output.add(allQuestions.get(randomIndex));
        }
        return output;
    }
}
