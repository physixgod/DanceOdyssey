package devnatic.danceodyssey.Interfaces;

import devnatic.danceodyssey.DAO.Entities.Delivery;

import java.util.List;

public interface IDeliveryService {
    public Delivery addLivraison (Delivery delivery);
    public Delivery updateLivraison (Delivery delivery);
    public Delivery findbyidLivraison (Integer idDel);
    void deleteLiv(Integer idDel);
    public List<Delivery> retrieveAllLiv();


}
