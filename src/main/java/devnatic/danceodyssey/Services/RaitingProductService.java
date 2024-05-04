package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.RatingProduct;
import devnatic.danceodyssey.DAO.Repositories.RaitingProductRepository;
import devnatic.danceodyssey.Interfaces.IRaitingProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RaitingProductService implements IRaitingProductService {
    private  final RaitingProductRepository raitingProductRepository;



    @Override
    public RatingProduct addRaiting(RatingProduct ratingProducts) {
        return raitingProductRepository.save(ratingProducts);
    }

}
