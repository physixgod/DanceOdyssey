package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Accommodation;

import java.util.List;
import java.util.Set;

public interface AccomdationIServices {
    Accommodation addAcc(int idDancer,Accommodation accommodation,int idEvent);
    String registerAcc(int idDancer , int idAcc , int nbPersonnes, Boolean br,Boolean lunch,Boolean dinner);
    Set<Accommodation> showEventAccomadations(int idEvent);
    String showAccPrice(int idAcc,int nbPeronnes, Boolean br,Boolean lunch,Boolean dinner);
    String registerAcc(int idDancer,int idAcc,int nbPersonnes);

}
