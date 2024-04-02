package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Competition;
import devnatic.danceodyssey.DAO.Entities.Dancer;
import devnatic.danceodyssey.DAO.Entities.JuryManager;
import devnatic.danceodyssey.DAO.Entities.Participate;
import devnatic.danceodyssey.DAO.Repositories.CompetitionRepository;
import devnatic.danceodyssey.DAO.Repositories.DancerRepsoitory;
import devnatic.danceodyssey.DAO.Repositories.JuryManagerRepository;
import devnatic.danceodyssey.DAO.Repositories.ParticipateRepository;
import io.github.classgraph.Resource;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.UrlResource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class JuryService implements IJuryService {
    ParticipateRepository participateRepository;
    JuryManagerRepository juryManagerRepository;
    DancerRepsoitory dancerRepsoitory;
    private CompetitionRepository competitionRepository;
    @Override
    public JuryManager addJury(JuryManager juryManager) {
        if (juryManager != null) {
            return juryManagerRepository.save(juryManager);
        } else {

            return null;
        }
    }
    @Override
    public JuryManager updateJuryManager(int id, JuryManager juryManager) {
        Optional<JuryManager> existingJuryManagerOptional = juryManagerRepository.findById(id);
        if (existingJuryManagerOptional.isPresent()) {
            JuryManager existingJuryManager = existingJuryManagerOptional.get();
            existingJuryManager.setExpertiseArea(juryManager.getExpertiseArea());
            existingJuryManager.setDiploma(juryManager.getDiploma());
            existingJuryManager.setLastName(juryManager.getLastName());
            existingJuryManager.setFirstName(juryManager.getFirstName());
            existingJuryManager.setEmail(juryManager.getEmail());
            existingJuryManager.setPassword(juryManager.getPassword());
            existingJuryManager.setTelNumber(juryManager.getTelNumber());
            return juryManagerRepository.save(existingJuryManager);
        } else {
            return null; // or throw an exception, depending on your requirements
        }
    }

    @Override
    public List<JuryManager> getAllJuries() {
        List<JuryManager> jurys = juryManagerRepository.findAll();
        List<JuryManager> output = new ArrayList<>();
        for (JuryManager j:jurys){
            if ((!j.isApproved())&&(!j.isRejected())){
                output.add(j);
            }
        }
        return output;
    }

    @Override
    public JuryManager getJuryManagerById(int id) {
        Optional<JuryManager> juryManagerOptional = juryManagerRepository.findById(id);
        return juryManagerOptional.orElse(null);
    }

    @Override
    public void deleteJuryManager(int id) {
        Optional<JuryManager> juryManagerOptional = juryManagerRepository.findById(id);
        juryManagerOptional.ifPresent(juryManager -> {
            juryManager.setArchived(true);
            juryManagerRepository.save(juryManager);
        });
    }


    @Override
    public List<JuryManager> searchJuryManagers(String searchTerm) {
        return juryManagerRepository.findByExpertiseAreaContainingOrDiplomaContainingOrLastNameContainingOrFirstNameContainingOrEmailContaining(searchTerm, searchTerm, searchTerm, searchTerm, searchTerm);
    }

    @Override
    public void approvejury(int id) {
        JuryManager juryManager=juryManagerRepository.findById(id).get();
        juryManager.setApproved(Boolean.TRUE);
        juryManagerRepository.save(juryManager);
    }

    @Override
    public void rejectJury(int id) {
        JuryManager j = juryManagerRepository.findById(id).get();
        j.setRejected(Boolean.TRUE);
        juryManagerRepository.save(j);
    }

    @Override
    public List<Competition> getAllCompetitions() {
        List<Competition> cps = competitionRepository.findAll();
        List<Competition> output =new ArrayList<>();
        for (Competition c:cps){
            if (Objects.equals(c.getStatus(), "Open"))
                output.add(c);
        }
        return output;
    }

    @Override
    public void setJuries(int idJury, int idCompetition) {
        JuryManager Jury=juryManagerRepository.findById(idJury).get();
        Competition c =competitionRepository.findById(idCompetition).get();
        Jury.getCompetitionsManagedByJuries().add(c);
        juryManagerRepository.save(Jury);
        competitionRepository.save(c);
    }

    @Override
    public Set<JuryManager> showAprrovedJuries() {
        Set<JuryManager> approvedjuries=new HashSet<>();
        List<JuryManager> juryManagers=juryManagerRepository.findAll();
        for (JuryManager juryManager:juryManagers){
            if ((juryManager.isApproved())&&(!juryManager.isRejected())){
                approvedjuries.add(juryManager);
            }
        }
        return approvedjuries;
    }

    @Override
    public Set<JuryManager> showNotAffectedJuries(int idCompetition) {
        Set<JuryManager> approvedJuries = showAprrovedJuries();
        Set<JuryManager> output = new HashSet<>();
        Competition c = competitionRepository.findById(idCompetition).orElse(null);
        if (c != null) {
            for (JuryManager j : approvedJuries) {
                boolean test = false;
                for (Competition competition : j.getCompetitionsManagedByJuries()) {
                    if (c.equals(competition)) {
                        test = true;
                        break;
                    }
                }
                if (!test) {
                    output.add(j);
                }
            }
        }
        return output;
    }

    @Override
    public Set<JuryManager> showAffectedJuries(int idCompetiton) {
        Set<JuryManager> affectedJuries=new HashSet<>();
        Set<JuryManager> approvedJuries = showAprrovedJuries();
        Competition competition=competitionRepository.findById(idCompetiton).get();
        for(JuryManager juryManager:approvedJuries){
            boolean test=false;
            for(Competition c:juryManager.getCompetitionsManagedByJuries()){
                if(c==competition){
                    test=true;
                }
            }
            if (test){
                affectedJuries.add(juryManager);
            }
        }
        return affectedJuries;
    }

    @Override
    public List<JuryManager> JuryByName(String name) {
        return juryManagerRepository.findByFirstNameContainingIgnoreCase(name);
    }
    @Override
    public Set<JuryManager> JuriesByName(int idCompetition, String name) {
        List<JuryManager> juryManagers1=juryManagerRepository.findByFirstNameContainingIgnoreCase(name);
        Set<JuryManager> output=new HashSet<>();
        Set<JuryManager> juryManagers=showNotAffectedJuries(idCompetition);
        for (JuryManager juryManager:juryManagers){
            for(JuryManager juryManager1:juryManagers1){
                if(juryManager==juryManager1){
                    output.add(juryManager1);
                }
            }
        }
        return output;
    }

    @Override
    public void uploadExcel(int competitionId, MultipartFile file) throws IOException {
        Competition competition = competitionRepository.findById(competitionId).get();
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file");
        }
        String uploadDirectory = "C:/xampp/htdocs/ExcelFile/";
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDirectory + fileName;
        Files.createDirectories(Paths.get(uploadDirectory));
        Files.copy(file.getInputStream(), Paths.get(filePath));
        competition.setExcelFile(filePath);
        competitionRepository.save(competition); //
    }
    public UrlResource downloadExcel(int competitionId) throws MalformedURLException {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        String excelFilePath = competition.getExcelFile();
        Path path = Paths.get(excelFilePath);

        if (!Files.exists(path)) {
            throw new RuntimeException("Excel file not found");
        }

        return new UrlResource(path.toUri());
    }


    public Map<String, Double> getParticipantScores(MultipartFile file) throws IOException {
        Map<String, Double> participantScores = new HashMap<>();
        List<Participate> participates=new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell idParticipant = row.getCell(0);
                Cell nameCell = row.getCell(1);
                Cell scoreCell = row.getCell(2);
                if (nameCell != null && scoreCell != null &&
                        nameCell.getCellType() == CellType.STRING &&
                        scoreCell.getCellType() == CellType.NUMERIC) {
                    String participantName = nameCell.getStringCellValue();
                    double score = scoreCell.getNumericCellValue();
                    participantScores.put(participantName, score);
                    Participate participate = participateRepository.findById((int) idParticipant.getNumericCellValue()).orElse(null);
                    participate.setCompetitionScore(score);
                    participateRepository.save(participate);
                    participates.add(participate);
                }
            }
        }
        participates.sort(Comparator.comparing(Participate::getCompetitionScore).reversed());

        for (Participate participate:participates){
            participate.setCompetitionRank(participates.indexOf(participate)+1);
            participateRepository.save(participate);
        }
        return participantScores;
    }

    public List<Map.Entry<String, Double>> sortParticipantScores(Map<String, Double> participantScores) {
        List<Map.Entry<String, Double>> sortedList = new ArrayList<>(participantScores.entrySet());
        sortedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        return sortedList;
    }

    @Override
    public Set<Competition> ShowMyCompetitons(int idJury) {
        JuryManager juryManager=juryManagerRepository.findById(idJury).get();
        return juryManager.getCompetitionsManagedByJuries();
    }


    public Map<String, Object> getParticipantDetails(int participantId) {
        Map<String, Object> participantDetails = new HashMap<>();
        Optional<Participate> participateOptional = participateRepository.findById(participantId);
        participateOptional.ifPresent(participate -> {
            participantDetails.put("score", participate.getCompetitionScore());
            participantDetails.put("rank", participate.getCompetitionRank());
        });
        return participantDetails;
    }
}


