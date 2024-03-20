package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.DAO.Entities.Competition;
import devnatic.danceodyssey.DAO.Entities.JuryImage;
import devnatic.danceodyssey.DAO.Entities.JuryManager;
import devnatic.danceodyssey.DAO.Repositories.ImageRepository;
import devnatic.danceodyssey.Services.IJuryService;
import devnatic.danceodyssey.Services.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Configuration
@EnableWebMvc
@RestController
@AllArgsConstructor
@RequestMapping("/jury")
@CrossOrigin(origins = "http://localhost:4200")
public class JuryRestController {
    IJuryService iJuryService;
    @PostMapping("/addJury")
    public JuryManager addJury(@RequestBody JuryManager juryManager) {
        return iJuryService.addJury(juryManager);
    }

    @PutMapping("/updatejury/{id}")
    public JuryManager updateJuryManager(@PathVariable int id, @RequestBody JuryManager juryManager) {
        return iJuryService.updateJuryManager(id, juryManager);
    }

    @GetMapping("/getAll")
    public List<JuryManager> getAllJuries() {
        return iJuryService.getAllJuries();
    }

    @GetMapping("/getjurybyid/{id}")
    public JuryManager getJuryManagerById(@PathVariable int id) {
        return iJuryService.getJuryManagerById(id);
    }

    @DeleteMapping("/deletejury/{id}")
    public void deleteJuryManager(@PathVariable int id) {
        iJuryService.deleteJuryManager(id);
    }
    @GetMapping("/juryManagers/search")
    public List<JuryManager> searchJuryManagers(@RequestParam String searchTerm) {
        return iJuryService.searchJuryManagers(searchTerm);
    }

    @PutMapping("/approvejury/{id}")
    public void approveJury(@PathVariable int id) {
         iJuryService.approvejury(id);
    }
    
    @PutMapping("/rejectjury/{id}")
    public void rejectJury(@PathVariable int id) {
        iJuryService.rejectJury(id);
    }

    @GetMapping("/competitions")
    public List<Competition>getAllCompetitions() {
        return iJuryService.getAllCompetitions();
    }

    @PostMapping("/setJury/{idCompetition}/{idJuries}")
    public void setJuries(@PathVariable int idCompetition, @PathVariable int idJuries) {
         iJuryService.setJuries(idJuries,idCompetition);
    }
    @GetMapping("showApprovedJuries")
    public Set<JuryManager> showApprovedJuries(){
        return iJuryService.showAprrovedJuries();
    }
    @GetMapping("showNotAffectedJuries/{id}")
    public Set<JuryManager> showNotAffectedJuries(@PathVariable("id") int idCompetition){
        return iJuryService.showNotAffectedJuries(idCompetition);
    }
    @GetMapping("showAffectedJuries/{id}")
    public Set<JuryManager> showAffectedJuries(@PathVariable("id") int idCompetition){
        return iJuryService.showAffectedJuries(idCompetition);
    }
    @GetMapping("SearchJuryByName/{name}")
    public List<JuryManager> JuryByName(@PathVariable("name") String name){
        return iJuryService.JuryByName(name);
    }
    @GetMapping("SearchJuries/{id}/{name}")
    public Set<JuryManager> JuriesByName(@PathVariable("id") int idCompetition,@PathVariable("name") String name){
        return iJuryService.JuriesByName(idCompetition,name);
    }
    @Autowired
    ImageRepository imageRepository;

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getImageForJury(@PathVariable Integer id) {
        try {
            System.out.println("Demande d'image pour le produit avec l'ID : " + id);

            List<JuryImage> imageList = imageRepository.findByJury_JuryID(id);

            if (imageList.isEmpty()) {
                System.out.println("Aucune image trouvée pour le produit avec l'ID : " + id);
                return ResponseEntity.notFound().build();
            }

            JuryImage imageData = imageList.get(0);
            byte[] imageBytes = devnatic.danceodyssey.util.ImageUtils.decompressImage(imageData.getJury_data());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            System.out.println("Image trouvée pour le produit avec l'ID : " + id);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Ajoutez un log pour capturer les exceptions possibles lors de la récupération de l'image
            System.err.println("Erreur lors de la récupération de l'image : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Autowired
    StorageService storageService;
    @PostMapping(value = "/uploadImage/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Integer id) throws IOException {
        ResponseEntity<String> uploadImageResponse = storageService.uploadImage(file, id);

        // Vérifiez si le téléchargement de l'image a réussi
        if (uploadImageResponse != null && uploadImageResponse.getStatusCode() == HttpStatus.OK) {
            // Retournez l'URL complète de l'image
            String imageUrl = "http://localhost:8086/DanceOdyssey/products/getProductImage/" + id;
            return ResponseEntity.ok(imageUrl);
        } else {
            // En cas d'échec, retournez le message d'erreur
            return ResponseEntity.status(uploadImageResponse != null ? uploadImageResponse.getStatusCode() : HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(uploadImageResponse != null ? uploadImageResponse.getBody() : "Image upload failed");
        }
    }

    @PostMapping("/{competitionId}/uploadExcel")
    public ResponseEntity<String> uploadExcelFile(@PathVariable int competitionId,
                                                  @RequestParam("file") MultipartFile file) {
        try {
            iJuryService.uploadExcel(competitionId, file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

}
