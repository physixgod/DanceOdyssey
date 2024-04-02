package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.ParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParentCategoryRepository extends JpaRepository<ParentCategory,Integer> {

ParentCategory findByType (String type);

}

