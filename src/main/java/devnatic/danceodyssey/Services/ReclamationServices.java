package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Reclamation;
import devnatic.danceodyssey.DAO.Repositories.ReclamationRepositories;
import devnatic.danceodyssey.RealTimeNotifications.RealNotifications;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ReclamationServices implements IReclamationServices{
    ReclamationRepositories reclamationRepositories;

    @Autowired
    private SimpMessagingTemplate template;

    // You may need a method to get the total number of reclamations for the notification
    private int getTotalReclamations() {
        // Get the total count of reclamations and cast it to int
        int totalCountInt = (int) reclamationRepositories.count();

        return totalCountInt;
    }

    @Override
    public Reclamation Addreclamation(Reclamation reclamation){
        Reclamation savedReclamation = reclamationRepositories.save(reclamation);

        // Send notification after saving
        RealNotifications notification = new RealNotifications(getTotalReclamations());
        template.convertAndSend("/topic/notification", notification);

        return savedReclamation;
    }



    @Override
    public Reclamation updateReclamationById(Integer id, Reclamation rec) {
        Optional<Reclamation> existingReclamationOptional = reclamationRepositories.findById(id);
        if (existingReclamationOptional.isPresent()) {
            Reclamation existingReclamation = existingReclamationOptional.get();
            // Update attributes of existingReclamation with attributes of rec
            existingReclamation.setReclamationDescription(rec.getReclamationDescription());
            existingReclamation.setReclamationResponse(rec.getReclamationResponse());
            existingReclamation.setReclamationReason(rec.getReclamationReason());
            existingReclamation.setUserRec(rec.getUserRec());
            existingReclamation.setDancerRec(rec.getDancerRec());

            // Save the updated reclamation back to the database
            return reclamationRepositories.save(existingReclamation);
        } else {
            // Handle case where reclamation with given id doesn't exist
            // For example, you can throw an exception or return null
            return null;
        }
    }

    @Override
    public List<Reclamation> Showreclamation(){
          return reclamationRepositories.findAll();
    }

    @Override
    public void Deletereclamation(Integer reclamationID){
        reclamationRepositories.deleteById(reclamationID);
    }

    @Override
    public Reclamation findReclamationById(int id){
        return reclamationRepositories.findReclamationByReclamationID(id);
    }

}
