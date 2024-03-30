package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
    boolean existsByRefProduct(Integer refProduct);

    @Query("SELECT MAX(p.idProduct) FROM Products p")
    Integer findMaxProductId();
    Optional<Products> findByRefProduct(Integer refProduct);
    Set<Products> findProductsByArchived(Boolean archived);
    Set<Products> findProductsByProductNameContainingIgnoreCase(String name);
    Set<Products> findProductsByCategoriesProduct_IdCategories(Integer categoryId);
    Set<Products> findProductsBySubCategoriesProduct_IdCategories(Integer subCategoryId);
    List<Products> findByProductNameContainingIgnoreCaseAndCategoriesProduct_IdCategories(String name, Integer categoryId);

    List<Products> findByProductNameContainingIgnoreCaseAndSubCategoriesProduct_IdCategories(String name, Integer subCategoryId);
    List<Products> findTop5ByOrderByDatePublicationDesc();

    @Query("SELECT p FROM Products p JOIN p.ratingProductsP rp WHERE rp.Score >= 4 ORDER BY rp.Score DESC")
    List<Products> findTop5ByOrderByRatingProductsPScoreDesc();
    List<Products> findByIsPromotion(Boolean isPromotion);
}
