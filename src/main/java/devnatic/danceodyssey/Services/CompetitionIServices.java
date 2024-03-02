package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Competition;
import devnatic.danceodyssey.DAO.Entities.Dancer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CompetitionIServices {
    public Competition AddCompetitionorUpdate(Competition c );
    public void DeleteCompetition (int competitionID );
    public List<Competition> ShowCompetition();
    public List<Competition> SearchCompetitionByName(String competitionName );
    public List<Competition> SeachCompetitionByLocation(String location);
    public List<Competition> SearchCompetitionStartDate(LocalDate startdate );
    public List<Competition> SeachCompetitionByDanceCategory(String danceCategory);
    public void CloseCompetitionEntrance(int idCompetition);
    public List<Dancer> ShowCompetitionDancers(int idCompetiton);
    public Map<String, Integer> showDancersRank(int idCompetition);
    List<Competition> showClosedCompetitions();
    public Competition getCompetitionByID(int id);
    void registerCompetition(int idDancer,int idCompetition);
}