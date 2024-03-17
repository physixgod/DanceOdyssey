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
}
