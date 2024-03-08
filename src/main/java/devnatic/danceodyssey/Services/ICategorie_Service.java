package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.CategoriesProduct;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ICategorie_Service {
    public CategoriesProduct AddnewCategorie(CategoriesProduct cr) ;
    public List<CategoriesProduct> getAllcategories() ;
    public void AddSubnewCategorie(CategoriesProduct cat) ;
    List<CategoriesProduct> getSubCategories(Integer parentId);
    public CategoriesProduct createCategoryWithSubcategories(String categoryName, List<String> subcategoryNames) ;
    public Set<CategoriesProduct> getParentcategories() ;


}
