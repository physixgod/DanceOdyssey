package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Progress;
import devnatic.danceodyssey.DAO.Entities.ProgressDTO;

import java.util.List;

public interface IProgressServices {
    List<Progress> getAllProgress();
    Progress getProgressById(Long id);
    Progress createProgress(Progress progress);
    Progress updateProgress(Long id, Progress progress);
    void deleteProgress(Long id);

    List<ProgressDTO> getProgressData();

}
