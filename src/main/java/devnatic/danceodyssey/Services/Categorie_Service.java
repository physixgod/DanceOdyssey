package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.CategoriesProduct;
import devnatic.danceodyssey.DAO.Repositories.CategoriesProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class Categorie_Service implements ICategorie_Service {
private  final CategoriesProductRepository categoriesProductRepository;


    @Override
    public CategoriesProduct AddnewCategorie(CategoriesProduct cr) {
        return categoriesProductRepository.save(cr);
    }

    @Override
    public List<CategoriesProduct> getAllcategories() {

    return categoriesProductRepository.findAll();
    }

    @Override
    public void AddSubnewCategorie(CategoriesProduct cat) {
        addsubCatergorie(cat);
        categoriesProductRepository.save(cat);
    }


    @Override
    public List<CategoriesProduct> getSubCategories(Integer parentId) {
        // Récupérer la catégorie parent
        Optional<CategoriesProduct> parentCategoryOptional = categoriesProductRepository.findById(parentId);

        if (parentCategoryOptional.isPresent()) {
            CategoriesProduct parentCategory = parentCategoryOptional.get();

            // Renvoyer la liste des sous-catégories de la catégorie parent
            return parentCategory.getSubCatergorie();
        } else {
            // Gérer le cas où la catégorie parent n'est pas trouvée
            log.error("Parent category with ID {} not found.", parentId);
            return Collections.emptyList();
        }
    }

    @Override
    public CategoriesProduct createCategoryWithSubcategories(String categoryName, List<String> subcategoryNames) {
        CategoriesProduct category = new CategoriesProduct(categoryName);

        // Create and link subcategories
        List<CategoriesProduct> subcategories = new ArrayList<>();
        for (String subcategoryName : subcategoryNames) {
            CategoriesProduct subcategory = new CategoriesProduct(subcategoryName);
            subcategories.add(subcategory);
            category.subCatergories().add(subcategory);
        }

        // Set the subcategories to the category
        category.setSubcategories(new HashSet<>(subcategories));

        // Save the category (which should also save the associated subcategories)
        return AddnewCategorie(category);
    }




    @Override
    public Set<CategoriesProduct> getParentcategories() {
        List<CategoriesProduct> categoriesProducts = categoriesProductRepository.findAll();
        Set<CategoriesProduct> output=new HashSet<>();
        for (CategoriesProduct cp : categoriesProducts) {
            if(!cp.getSubCatergorie().isEmpty()){
                output.add(cp);
            }
        }
        return output;
    }


    private void addsubCatergorie ( CategoriesProduct categoryy)
    {
        for( CategoriesProduct subCateroy : categoryy.subCatergories() ) {
            addsubCatergorie(subCateroy);
            categoriesProductRepository.save(subCateroy);
        }
    }

}
