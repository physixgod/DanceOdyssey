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
        Optional<CategoriesProduct> parentCategoryOptional = categoriesProductRepository.findById(parentId);

        if (parentCategoryOptional.isPresent()) {
            CategoriesProduct parentCategory = parentCategoryOptional.get();

            return parentCategory.getSubCatergorie();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public CategoriesProduct createCategoryWithSubcategories(String categoryName, List<String> subcategoryNames) {
        CategoriesProduct category = new CategoriesProduct(categoryName);

        List<CategoriesProduct> subcategories = new ArrayList<>();
        for (String subcategoryName : subcategoryNames) {
            CategoriesProduct subcategory = new CategoriesProduct(subcategoryName);
            subcategories.add(subcategory);
            category.subCatergories().add(subcategory);
        }

        category.setSubcategories(new HashSet<>(subcategories));

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

    @Override
    public void addSubCategoriesToParent(Integer parentId, List<String> subcategoryNames) {
        Optional<CategoriesProduct> parentOptional = categoriesProductRepository.findById(parentId);

        if (parentOptional.isPresent()) {
            CategoriesProduct parentCategory = parentOptional.get();

            for (String subcategoryName : subcategoryNames) {
                CategoriesProduct subcategory = new CategoriesProduct(subcategoryName);
                parentCategory.subCatergories().add(subcategory);
            }

            categoriesProductRepository.save(parentCategory);
        }
    }


    private void addsubCatergorie ( CategoriesProduct categoryy)
    {
        for( CategoriesProduct subCateroy : categoryy.subCatergories() ) {
            addsubCatergorie(subCateroy);
            categoriesProductRepository.save(subCateroy);
        }
    }

}
