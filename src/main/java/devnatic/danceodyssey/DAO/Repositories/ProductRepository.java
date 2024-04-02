package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.ParentCategory;
import devnatic.danceodyssey.DAO.Entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {

    @Query("SELECT MAX(p.idProduct) FROM Products p")
    Integer findMaxProductId();

    Optional<Products> findByRefProduct(Integer refProduct);

    Set<Products> findProductsByArchived(Boolean archived);

    Set<Products> findProductsByProductNameContainingIgnoreCase(String name);


    List<Products> findTop5ByOrderByDatePublicationDesc();


    @Query("SELECT p FROM Products p JOIN p.ratingProductsP rp GROUP BY p.idProduct ORDER BY rp.score DESC limit 5")
    List<Products> findTop5ByOrderBySumOfScoresDesc();
    List<Products> findByIsPromotion(Boolean isPromotion);
    List<Products> findByParentCategory(ParentCategory parentCategory);
    List<Products> findByParentCategory_Id(Integer parentId);


}
