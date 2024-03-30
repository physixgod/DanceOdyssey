package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.RatingProducts;
import devnatic.danceodyssey.DAO.Repositories.RaitingProductRepository;
import devnatic.danceodyssey.Filter.FILT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RatingProduct_Service implements IRaitingProduct {
    private  final RaitingProductRepository raitingProductRepository;

    @Override
    public RatingProducts AddNEwRaitingProduit(RatingProducts Rp) {

        String output = FILT.getCensoredText(Rp.getFeedBack());
        System.out.println(output);
        Rp.setFeedBack(output);

        return raitingProductRepository.save(Rp);
    }
}
