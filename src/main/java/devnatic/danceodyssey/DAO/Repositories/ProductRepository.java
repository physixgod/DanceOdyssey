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


    Set<Products> findProductsByArchived(Boolean archived);

    Set<Products> findProductsByProductNameContainingIgnoreCase(String name);


    List<Products> findTop5ByOrderByDatePublicationDesc();


    @Query("SELECT p FROM Products p JOIN p.ratingProductsP rp GROUP BY p.idProduct ORDER BY rp.score DESC limit 5")
    List<Products> findTop5ByOrderBySumOfScoresDesc();
    List<Products> findByIsPromotion(Boolean isPromotion);
    List<Products> findByParentCategory(ParentCategory parentCategory);
    Set<Products> findByParentCategoryAndProductNameContainingIgnoreCase(ParentCategory parentCategory, String productName);
    List<Products> findProductsBySubCategories_Id(Integer subCategoryId);
    List<Products> findTop10ByOrderByQuantiteVendueDesc();

    @Query("SELECT p FROM Products p WHERE p.parentCategory.id = ?1 ORDER BY p.quantiteVendue DESC LIMIT 5")
    List<Products> findTop5ByParentCategory_IdOrderByQuantiteVendueDesc(Integer parentCategoryId);
    @Query("SELECT p FROM Products p WHERE p.parentCategory.id = ?1 AND p.isPromotion = TRUE ORDER BY p.idProduct DESC LIMIT 5")
    List<Products> findPromotionalProductsByParentCategoryId(Integer parentCategoryId);
    List<Products> findTop5ByParentCategoryOrderByDatePublicationDesc(ParentCategory parentCategory);

    @Query("SELECT p FROM Products p WHERE p.parentCategory.id = ?1 ORDER BY p.avreageScore DESC LIMIT 5")
    List<Products> findTop5ByParentCategoryIdOrderByAverageScoreDesc(Integer parentCategoryId);




}
