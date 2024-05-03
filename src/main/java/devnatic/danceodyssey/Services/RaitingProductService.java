package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.RaitingProductss;
import devnatic.danceodyssey.DAO.Repositories.RaitingProductRepository;
import devnatic.danceodyssey.Interfaces.IRaitingProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RaitingProductService implements IRaitingProductService {
    private  final RaitingProductRepository raitingProductRepository;



    @Override
    public RaitingProductss addRaiting(RaitingProductss ratingProducts) {
        return raitingProductRepository.save(ratingProducts);
    }

}
