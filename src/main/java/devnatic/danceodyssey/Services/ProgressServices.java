package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Progress;
import devnatic.danceodyssey.DAO.Entities.ProgressDTO;
import devnatic.danceodyssey.DAO.Repositories.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressServices implements IProgressServices {

    private final ProgressRepository progressRepository;

    @Autowired
    public ProgressServices(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }



    @Override
    public List<Progress> getAllProgress() {
        return progressRepository.findAll();
    }

    @Override
    public Progress getProgressById(Long id) {
        Optional<Progress> optionalProgress = progressRepository.findById(id);
        return optionalProgress.orElse(null);
    }

    @Override
    public Progress createProgress(Progress progress) {
        return progressRepository.save(progress);
    }

    @Override
    public Progress updateProgress(Long id, Progress progress) {
        if (progressRepository.existsById(id)) {
            progress.setId(id); // Ensure the correct ID is set for the entity
            return progressRepository.save(progress);
        } else {
            return null;
        }
    }

    @Override
    public void deleteProgress(Long id) {
        progressRepository.deleteById(id);
    }

    @Override
    public List<ProgressDTO> getProgressData() {
        List<ProgressDTO> list = new ArrayList<>();

        List<Progress> progresses = progressRepository.findAll();

        int previousGoal = 0;
        for (int i = 0; i < progresses.size(); i++) {
            Progress progress = progresses.get(i);
            ProgressDTO dto = new ProgressDTO();
            dto.setDate(progress.getCreationDate());


            dto.setCalories(calculateCalories(progress.getDuration(),progress.getDifficulty(),progress.getWeight()));

            dto.setGoalAchieved(previousGoal<dto.getCalories());

            dto.setGoal(previousGoal);
            list.add(dto);

            if (i < progresses.size() - 1) {
                previousGoal = progresses.get(i).getNextGoal();
            }
        }

        return list;
    }

    public int calculateCalories(String duration, int difficulty, int weight ){
        int hours = Integer.parseInt(duration.split(":")[0]);
        int minutes = Integer.parseInt(duration.split(":")[1]);
        double durationInHours = hours + (minutes / 60.0);

        return (int) (difficulty * weight * durationInHours);

    }




}
