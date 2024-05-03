package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.*;
import devnatic.danceodyssey.DAO.Repositories.CompetitionRepository;
import devnatic.danceodyssey.DAO.Repositories.DancerRepository;
import devnatic.danceodyssey.DAO.Repositories.ParticipateRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class CompetitionServices implements CompetitionIServices {
    CompetitionRepository competitionRepository;
    DancerRepository dancerRepository;
    ParticipateRepository participateRepository;
    Environment environment;
    NameFile fileNamingUtil;
    FileUtil utils;
    @Override
    public Competition updateCompetitionImage(int idCompetition, MultipartFile competitionImage) {
        Competition competition= competitionRepository.findById(idCompetition).get();
        try {
            if (competitionImage != null && !competitionImage.isEmpty() && competitionImage.getSize() > 0) {
                String uploadDir = environment.getProperty("upload.competition.images");

                String newPhotoName = fileNamingUtil.nameFile(competitionImage);
                competition.setCompetitionImage(newPhotoName);



                utils.saveNewFile(uploadDir, newPhotoName, competitionImage);
            }
            return competitionRepository.save(competition);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update competition photo", e);
        }
    }

    @Override
    public String getImageUrlForCompetitionByID(int idCompetition) {
        Competition competition=competitionRepository.findById(idCompetition).get();
        String baseUrl = environment.getProperty("export.competition.images");
        String competitionImage = competition.getCompetitionImage();

        if (competitionImage != null && !competitionImage.isEmpty()) {

            System.err.println(baseUrl + competitionImage);
            return baseUrl + competitionImage;
        }

        return null;
    }

    @Override
    public Competition addCompetitionWithImage(Competition c, MultipartFile competitionImage) {
        if (c.getStartDate().isBefore(c.getEndDate())) {
            try {
                // Set status and current participants
                c.setStatus("Open");
                c.setCurrentParticipants(0);

                // Save or update competition
                Competition savedCompetition = competitionRepository.save(c);

                // Check if an image is provided
                if (competitionImage != null && !competitionImage.isEmpty() && competitionImage.getSize() > 0) {
                    String uploadDir = environment.getProperty("upload.competition.images");
                    String oldPhotoName = savedCompetition.getCompetitionImage();
                    String newPhotoName = fileNamingUtil.nameFile(competitionImage);
                    savedCompetition.setCompetitionImage(newPhotoName);

                    // Delete old image if exists
                    if (oldPhotoName != null) {
                        utils.deleteFile(uploadDir, oldPhotoName);
                    }

                    // Save new image
                    utils.saveNewFile(uploadDir, newPhotoName, competitionImage);
                }

                return savedCompetition;
            } catch (IOException e) {
                throw new RuntimeException("Failed to add or update competition", e);
            }
        } else {
            // Handle invalid date range
            throw new IllegalArgumentException("End date must be after start date");
        }
    }

    @Override
    public void autoCloseCompetition() {
        List<Competition>competitions=competitionRepository.findCompetitionsByStartDateIsBefore(LocalDate.now());
        for(Competition c:competitions){
            if (!Objects.equals(c.getStatus(), "Closed")) {
                c.setStatus("Closed");
                System.err.println(c.getCompetitionName());
                competitionRepository.save(c);
            }
        }
    }

    @Override
    public void maxParticipantsAttended() {
        List<Competition> competitions=competitionRepository.findByStatus("Open");
        for (Competition competition:competitions){
            if (competition.getCurrentParticipants()>=competition.getMaxParticipants())
                competition.setStatus("Closed");
        }
    }


    @Override
    public Competition AddCompetitionorUpdate(Competition c) {
        if (c.getStartDate().isBefore(c.getEndDate())) {
            c.setStatus("Open");
            c.setCurrentParticipants(0);
            return competitionRepository.save(c);
        }
        return c;
    }

    @Override
    public void DeleteCompetition(int id) {
        Competition c = competitionRepository.findById(id).get();
        if (!c.getParticipations().isEmpty()) {
            Set<Participate> ps = c.getParticipations();
            Set<Dancer> dancers = new HashSet<>();
            for (Participate participate : ps) {
                dancers.add(participate.getDancerParticipated());
            }
            for (Dancer d : dancers) {
                String msg = "Sorry dear Participant , " + d.getFirstName() + " " + d.getLastName() +
                        " We are sorry to inform you that the Competition Named :  "
                        + c.getCompetitionName() + " has been cancelled . ";
            }
            competitionRepository.delete(c);
        }
    }

    public List<Competition> ShowCompetition() {
        List<Competition> cps = competitionRepository.findAll();
        List<Competition> output = new ArrayList<>();
        for (Competition c : cps) {
            if (Objects.equals(c.getStatus(), "Open"))
                output.add(c);
        }
        return output;

    }

    public List<Competition> SearchCompetitionByName(String competitionName) {
        List<Competition> cps = competitionRepository.findCompetitionsByCompetitionNameContainingIgnoreCase(competitionName);
        List<Competition> output = new ArrayList<>();
        for (Competition c : cps) {
            if (Objects.equals(c.getStatus(), "Open"))
                output.add(c);
        }
        return output;
    }

    @Override
    public List<Competition> SeachCompetitionByLocation(String location) {
        return competitionRepository.findCompetitionByLocationIsContainingIgnoreCase(location);
    }

    @Override
    public List<Competition> SearchCompetitionStartDate(LocalDate startdate) {
        return competitionRepository.findCompetitionByStartDateBefore(startdate);
    }

    @Override
    public List<Competition> SeachCompetitionByDanceCategory(String danceCategory) {
        return competitionRepository.findCompetitionByDanceCategoryContainingIgnoreCase(danceCategory);
    }

    @Override
    public void CloseCompetitionEntrance(int idCompetition) {
        Competition c = competitionRepository.findById(idCompetition).get();
        c.setStatus("Closed");
        competitionRepository.save(c);
    }

    @Override
    public List<Dancer> ShowCompetitionDancers(int idCompetiton) {
        Set<Participate> ps = competitionRepository.findById(idCompetiton).get().getParticipations();
        List<Dancer> output = new ArrayList<>();
        for (Participate p : ps) {
            output.add(p.getDancerParticipated());
        }
        return output;
    }

    @Override
    public Map<String, Integer> showDancersRank(int idCompetition) {
        Map<String, Integer> output = new TreeMap<>();
        Set<Participate> ps = competitionRepository.findById(idCompetition).get().getParticipations();
        for (Participate p : ps) {
            output.put(p.getDancerParticipated().getFirstName() + "  " + p.getDancerParticipated().getLastName(), p.getCompetitionRank());
        }
        output = sortByValue(output);

        return output;
    }


    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @Override
    public List<Competition> showClosedCompetitions() {
        return competitionRepository.findByStatus("Closed");
    }

    @Override
    public Competition getCompetitionByID(int id) {
        return competitionRepository.findById(id).get();
    }

    @Override
    public void registerCompetition(int idDancer, int idCompetition) {
        Dancer dancer = dancerRepository.findById(idDancer).get();
        Competition competition = competitionRepository.findById(idCompetition).get();
        Set<Dancer> dancers = getCompetitionDancers(idCompetition);
        boolean test = true;
        for (Dancer d : dancers) {
            if (d == dancer) {
                test = false;
                break;
            }
        }
        if (test) {
            Participate p = new Participate();
            p.setCompetition(competition);
            p.setDancerParticipated(dancer);
            competition.setCurrentParticipants(competition.getCurrentParticipants() + 1);
            if (competition.getCurrentParticipants() == competition.getMaxParticipants()) {
                competition.setStatus("Closed");
            }
            competitionRepository.save(competition);
            participateRepository.save(p);
        }
    }

    @Override
    public Set<Dancer> getCompetitionDancers(int idCompetition) {
        Competition competition = competitionRepository.findById(idCompetition).get();
        Set<Dancer> dancers = new HashSet<>();
        for (Participate participate : competition.getParticipations()) {
            dancers.add(participate.getDancerParticipated());
        }
        return dancers;
    }

    @Override
    public Map<String, String> showMyCompetitons(int idDancer) {
        Dancer d = dancerRepository.findById(idDancer).get();
        Set<Participate> participates = d.getParticipates();
        Map<String, String> output = new TreeMap<>();
        for (Participate p : participates) {
            if (p.getCompetitionRank() == 0) {
                output.put(p.getCompetition().getCompetitionName(), "Not evaluated yet");
            }
            else if (p.getCompetitionRank() !=0 ){
                output.put(p.getCompetition().getCompetitionName(),String.valueOf(p.getCompetitionRank()));
            }
        }

        System.err.println(participateRepository.findByDancerParticipatedDancerId(idDancer).toString());
        System.err.println(participateRepository.findByCompetitionCompetitionIDOrderByCompetitionRankAsc(19).toString());

        return output;
    }
}