package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Feedback;
import devnatic.danceodyssey.DAO.Repositories.FeedbackRepositories;
import lombok.AllArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class FeedbackServices implements IFeedbackservices{
    FeedbackRepositories feedbackRepositories;
    @Override
    public Feedback AddFeedback(Feedback feedback){
        return feedbackRepositories.save(feedback);
    }

    @Override
    public Feedback Updatefeedback(int feedbackId, boolean resolved) {
        Feedback existingFeedback = feedbackRepositories.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + feedbackId));


        return feedbackRepositories.save(existingFeedback);
    }
    @Override
    public List<Feedback> Showfeedback(){
        return feedbackRepositories.findAll();
    }

    @Override
    public Feedback FindfeedbackbyID(int id){
        return feedbackRepositories.findFeedbackByFeedbackID(id);
    }

}
