package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.DAO.Entities.Competition;
import devnatic.danceodyssey.DAO.Entities.Dancer;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Repositories.CompetitionRepository;
import devnatic.danceodyssey.DAO.Repositories.ProductRepository;
import devnatic.danceodyssey.Services.CompetitionIServices;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("/competition")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CompetitionRestController {
    CompetitionIServices competitionIServices;


    @PostMapping("AddCompetitionorUpdate")
    public Competition AddCompetitionorUpdate(@RequestBody Competition c) {
        return competitionIServices.AddCompetitionorUpdate(c);
    }

    @DeleteMapping("DeleteCompetition/{id}")
    public void DeleteCompetition(@PathVariable("id") int id) {
        competitionIServices.DeleteCompetition(id);
    }

    @GetMapping("ShowCompetitions")
    public List<Competition> ShowCompetition() {

        return competitionIServices.ShowCompetition();
    }

    @GetMapping("SearchCompetitionByName/{name}")
    public List<Competition> SearchCompetitionByName(@PathVariable("name") String competitionName) {
        return competitionIServices.SearchCompetitionByName(competitionName);
    }

    @GetMapping("SearchCompetitionByLocation/{location}")
    public List<Competition> SeachCompetitionByLocation(@PathVariable("location") String location) {
        return competitionIServices.SeachCompetitionByLocation(location);
    }

    @GetMapping("SearchCompetitionStartDate/{date}")
    public List<Competition> SearchCompetitionByStartDate(@PathVariable("date") LocalDate startDate) {
        return competitionIServices.SearchCompetitionStartDate(startDate);
    }

    @GetMapping("SeachCompetitionByDanceCategory/{dance}")
    public List<Competition> SeachCompetitionByDanceCategory(@PathVariable("dance") String danceCategory) {
        return competitionIServices.SeachCompetitionByDanceCategory(danceCategory);
    }

    @PutMapping("UpdateCompetitionStatus/{id}")
    public void UpdateCompetitionStatus(@PathVariable("id") int id) {
        competitionIServices.CloseCompetitionEntrance(id);
    }
    @GetMapping("showCompetitonDancers/{id}")
    public List<Dancer>showCompetitonDancers(@PathVariable("id") int id){
        return competitionIServices.ShowCompetitionDancers(id);
    }
    @GetMapping("showDancersRank/{id}")
    public Map<String,Integer> showDancersRank(@PathVariable("id") int id){
        return competitionIServices.showDancersRank(id);
    }
    @GetMapping("showClosedCompetition")
    public List<Competition> showClosedCompetition(){
        return competitionIServices.showClosedCompetitions();
    }
    @GetMapping("getcp/{id}")
    public Competition getcp(@PathVariable("id")int id ){
        return competitionIServices.getCompetitionByID(id);
    }
    @PostMapping("Register/{idC}/{idD}")
    public void affecterDancerCompetition(@PathVariable("idC") int idC,@PathVariable("idD") int idD){
        competitionIServices.registerCompetition(idD,idC);
    }
    @GetMapping("getCompetitionDancers/{id}")
    public Set<Dancer> getCompetitionDancers(@PathVariable("id") int idCompetition){
        return competitionIServices.getCompetitionDancers(idCompetition);
    }
    @GetMapping("getMyCompetitions/{id}")
    public Map<String, String> showMyCompetitons(@PathVariable(("id")) int idDancer){
        return competitionIServices.showMyCompetitons(idDancer);
    }
    @GetMapping("getCompetitionImage/{id}")
    public String getCompetitionImage(@PathVariable("id") int idCompetition){
        return competitionIServices.getImageUrlForCompetitionByID(idCompetition);
    }
    @PostMapping("uploadCompetitionImage/image/{id}")
    public Competition updateCompetitionImage(@PathVariable("id")int idCompetition, @RequestParam("image")MultipartFile image){
        return competitionIServices.updateCompetitionImage(idCompetition,image);
    }
    @PostMapping("gainPoints/{idDancer}")
    public int gainPoints(@RequestParam int myScore,@RequestParam int pcScore,@PathVariable("idDancer") int idDancer ){
        return competitionIServices.gainPoints(myScore,pcScore,idDancer);
    }
    ProductRepository productRepository;

    @GetMapping("GetAll")
    public List<Products> getProdcuts(){
        return productRepository.findAll();
    }
}
