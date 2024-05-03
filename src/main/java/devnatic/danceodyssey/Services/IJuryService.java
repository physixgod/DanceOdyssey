package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Competition;
import devnatic.danceodyssey.DAO.Entities.JuryManager;
import io.github.classgraph.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IJuryService {

    JuryManager addJury(JuryManager juryManager);
    JuryManager updateJuryManager(int id, JuryManager juryManager);
    List<JuryManager> getAllJuries();
    JuryManager getJuryManagerById(int id);
    void deleteJuryManager(int id);
    List<JuryManager> searchJuryManagers(String searchTerm);
    public void approvejury(int id);
    public  void rejectJury(int id);
    List<Competition> getAllCompetitions();
    void setJuries(int  idJuries,int idCompetition);
    Set<JuryManager> showAprrovedJuries();
    Set<JuryManager> showNotAffectedJuries(int idCompetition);
    Set<JuryManager> showAffectedJuries(int idCompetiton);
    List<JuryManager> JuryByName(String name );
    Set<JuryManager> JuriesByName(int idCompetition, String name);
    void uploadExcel(int competitionId, MultipartFile file) throws IOException;
    Map<String, Double> getParticipantScores(MultipartFile file) throws IOException ;
    List<Map.Entry<String, Double>> sortParticipantScores(Map<String, Double> participantScores);
    Set<Competition> ShowMyCompetitons(int idJury);
    public UrlResource downloadExcel(int competitionId) throws MalformedURLException;

    Map<String, Object> getParticipantDetails(int participantId);


}