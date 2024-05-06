package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Feedback;

import java.util.List;

public interface IFeedbackservices {
    public Feedback AddFeedback(Feedback feedback);
    public Feedback Updatefeedback(int feedbackId, boolean resolved);
    public List<Feedback> Showfeedback();
    public Feedback FindfeedbackbyID(int id);
}


