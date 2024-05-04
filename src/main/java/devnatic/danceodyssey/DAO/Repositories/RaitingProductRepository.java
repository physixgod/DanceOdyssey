package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Entities.RatingProduct;
import devnatic.danceodyssey.DAO.Entities.RatingProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RaitingProductRepository extends JpaRepository<RatingProduct,Integer> {

}
