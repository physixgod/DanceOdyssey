package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Delivery;
import devnatic.danceodyssey.DAO.Repositories.DeliveryRepository;
import devnatic.danceodyssey.Interfaces.IDeliveryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DeliveryService implements IDeliveryService {
    private  final DeliveryRepository deliveryRepository;
    @Override
    public Delivery addLivraison(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery updateLivraison(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery findbyidLivraison(Integer idDel) {
        return deliveryRepository.findById(idDel).orElse(null);
    }

    @Override
    public void deleteLiv(Integer idDel) {
        deliveryRepository.deleteById(idDel);

    }

    @Override
    public List<Delivery> retrieveAllLiv() {
        return deliveryRepository.findAll();
    }


}
