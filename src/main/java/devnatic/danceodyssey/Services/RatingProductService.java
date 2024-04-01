package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.RatingProducts;
import devnatic.danceodyssey.DAO.Repositories.RaitingProductRepository;
import devnatic.danceodyssey.Interfaces.IRaitingProduct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RatingProductService implements IRaitingProduct {
    private  final RaitingProductRepository raitingProductRepository;

    @Override
    public RatingProducts addNEwRaitingProduit(RatingProducts Rp) {
        Rp.setFeedBack(Rp.getFeedBack());
        return raitingProductRepository.save(Rp);
    }
}
