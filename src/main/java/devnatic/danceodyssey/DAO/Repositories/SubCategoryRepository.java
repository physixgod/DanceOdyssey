package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Integer> {


}
