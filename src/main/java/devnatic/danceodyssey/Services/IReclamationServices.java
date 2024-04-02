package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Reclamation;

import java.util.List;

public interface IReclamationServices {
    Reclamation Addreclamation(Reclamation reclamation);
    public Reclamation updateReclamationById(Integer id, Reclamation rec);
    public List<Reclamation> Showreclamation();
    public void Deletereclamation(Integer reclamationID);
    public Reclamation findReclamationById(int id);
}
