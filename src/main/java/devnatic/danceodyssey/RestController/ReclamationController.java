package devnatic.danceodyssey.RestController;

/*import devnatic.danceodyssey.DAO.Entities.Reclamation;

import devnatic.danceodyssey.Services.IReclamationServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/,http://localhost:8086")
@RequestMapping("/reclamation")
public class ReclamationController {
    private final IReclamationServices reclamationServices;

    @PostMapping("/AddReclamation")
    public ResponseEntity<Reclamation> Addreclamation(@RequestBody Reclamation reclamation){
        Reclamation newReclamarion =reclamationServices.Addreclamation(reclamation);
        return new ResponseEntity<>(newReclamarion,HttpStatus.CREATED);
    }



    @PutMapping("/UpdateReclamation/{id}")
    public ResponseEntity<Reclamation> updateReclamation(@PathVariable Integer id, @RequestBody Reclamation reclamation) {
        Reclamation updatedReclamation = reclamationServices.updateReclamationById(id, reclamation);
        if (updatedReclamation != null) {
            return new ResponseEntity<>(updatedReclamation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Or handle as you wish
        }
    }

    @GetMapping("/Showall")
    public ResponseEntity<List<Reclamation>> ShowReclamation(){
      List<Reclamation> reclamations = reclamationServices.Showreclamation();
      return new ResponseEntity<>(reclamations, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Reclamation> GetReclamationbyId(@PathVariable("id") int id){
        Reclamation reclamations = reclamationServices.findReclamationById(id);
        return new ResponseEntity<>(reclamations, HttpStatus.OK);
    }

    @DeleteMapping("/deletereclamation/{id}")
    public ResponseEntity<Reclamation> Deletereclamation(@PathVariable("id") int id){
        reclamationServices.Deletereclamation(id);
        return new ResponseEntity<>(HttpStatus.OK) ;
    }


}*/


