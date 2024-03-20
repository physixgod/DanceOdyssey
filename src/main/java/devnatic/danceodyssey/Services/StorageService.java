package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.JuryImage;
import devnatic.danceodyssey.DAO.Entities.JuryManager;
import devnatic.danceodyssey.DAO.Repositories.JuryManagerRepository;
import devnatic.danceodyssey.DAO.Repositories.StoreageRepository;
import devnatic.danceodyssey.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class StorageService {

    private final JuryManagerRepository juryManagerRepository;
    private final StoreageRepository storageRepository;

    @Transactional
    public ResponseEntity<String> uploadImage(MultipartFile file, Integer id) {
        try {
            Optional<JuryManager> optionalJuryManager = juryManagerRepository.findById(id);

            if (optionalJuryManager.isPresent()) {
                JuryManager juryManager = optionalJuryManager.get();

                JuryImage imageData = JuryImage.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .jury_data(ImageUtils.compressImage(file.getBytes()))
                        .jury(juryManager) // Corrected the field name
                        .build();

                // Save image data
                storageRepository.save(imageData);

                // Set the image data to the jury manager's imageUrl
                juryManager.setImageUrl(imageData.getName());

                // Save the jury manager
                juryManagerRepository.save(juryManager);

                return ResponseEntity.ok().body("File uploaded successfully: " + file.getOriginalFilename());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            log.error("Error uploading file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<String> updateImage(MultipartFile file, Integer id) {
        try {
            Optional<JuryManager> optionalJuryManager = juryManagerRepository.findById(id);

            if (optionalJuryManager.isPresent()) {
                JuryManager juryManager = optionalJuryManager.get();

                // Assuming the jury manager has only one image
                JuryImage juryImage = juryManager.getJury_data().stream().findFirst().orElse(null);

                if (juryImage != null) {
                    juryImage.setName(file.getOriginalFilename());
                    juryImage.setType(file.getContentType());
                    juryImage.setJury_data(ImageUtils.compressImage(file.getBytes()));

                    storageRepository.save(juryImage);

                    return ResponseEntity.ok().body("File updated successfully: " + file.getOriginalFilename());
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            log.error("Error updating file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating file: " + e.getMessage());
        }
    }
}
