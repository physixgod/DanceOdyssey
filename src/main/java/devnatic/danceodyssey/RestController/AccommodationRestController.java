package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.DAO.Entities.Accommodation;
import devnatic.danceodyssey.DAO.Entities.Event;
import devnatic.danceodyssey.Services.AccomdationIServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/event")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AccommodationRestController {
    AccomdationIServices accomdationIServices;
    @PostMapping("AddACC/{idDancer}/{idEvent}")
    public Accommodation AddAcc(@PathVariable("idDancer")int idDancer,@PathVariable("idEvent") int idEvent, @RequestBody Accommodation accommodation) {
        return accomdationIServices.addAcc(idDancer,accommodation,idEvent);
    }
    @PostMapping("registerACC/{idDancer}/{idAcc}")
    public String registerAcc(@PathVariable("idDancer")int idDancer ,@PathVariable("idAcc") int idAcc ,@RequestParam  int nbPersonnes, @RequestParam Boolean br, @RequestParam Boolean lunch,@RequestParam  Boolean dinner){
        return accomdationIServices.registerAcc(idDancer,idAcc,nbPersonnes,br,lunch,dinner);
    }
    @GetMapping("ShowAcc/{idEvent}")
    public Set<Accommodation> ShowAcc(@PathVariable("idEvent")int idEvent){
        return accomdationIServices.showEventAccomadations(idEvent);
    }
    @PostMapping("register/{idDancer}/{idAcc}")
    public String register(@PathVariable("idDancer") int idDancer,@PathVariable("idAcc") int idAcc, @RequestParam int nbPersonnes){
        return accomdationIServices.registerAcc(idDancer,idAcc,nbPersonnes);
    }
    @GetMapping("GetPrice/{idAcc}")
    public String showAccPrice(@PathVariable("idAcc")int idAcc, @RequestParam int nbPersonnes,@RequestParam  Boolean br,@RequestParam  Boolean lunch, @RequestParam Boolean dinner){
        return accomdationIServices.showAccPrice(idAcc,nbPersonnes,br,lunch,dinner);
    }


}
