package devnatic.danceodyssey.RestController;
import devnatic.danceodyssey.DAO.Entities.Progress;
import devnatic.danceodyssey.DAO.Entities.ProgressDTO;
import devnatic.danceodyssey.Services.IProgressServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "*")
public class ProgressController {

    private final IProgressServices progressService;

    @Autowired
    public ProgressController(IProgressServices progressService) {
        this.progressService = progressService;
    }

    @GetMapping
    public ResponseEntity<List<Progress>> getAllProgress() {
        List<Progress> progressList = progressService.getAllProgress();
        return new ResponseEntity<>(progressList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Progress> getProgressById(@PathVariable Long id) {
        Progress progress = progressService.getProgressById(id);
        if (progress != null) {
            return new ResponseEntity<>(progress, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Progress> createProgress(@RequestBody Progress progress) {
        Progress createdProgress = progressService.createProgress(progress);
        return new ResponseEntity<>(createdProgress, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Progress> updateProgress(@PathVariable Long id, @RequestBody Progress progress) {
        Progress updatedProgress = progressService.updateProgress(id, progress);
        if (updatedProgress != null) {
            return new ResponseEntity<>(updatedProgress, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        progressService.deleteProgress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/data")
    public ResponseEntity<List<ProgressDTO>> getAllProgressData() {
        List<ProgressDTO> progressList = progressService.getProgressData();
        return new ResponseEntity<>(progressList, HttpStatus.OK);
    }
}

