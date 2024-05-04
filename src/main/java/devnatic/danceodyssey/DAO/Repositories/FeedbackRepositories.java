package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepositories extends JpaRepository<Feedback,Integer> {
   Feedback findFeedbackByFeedbackID (Integer feedbackID);

}
