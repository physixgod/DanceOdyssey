package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Annulation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAnnulationService {

    ResponseEntity<Annulation> createAnnulation(Annulation annulation, int courseID);

    List<Annulation> getAllAnnulations();


    }
