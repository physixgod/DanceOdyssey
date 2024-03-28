package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.DAO.Entities.CategoriesProduct;
import devnatic.danceodyssey.DAO.Entities.CategoryCreationRequest;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.Services.ICategorie_Service;
import devnatic.danceodyssey.Services.IProduct_Services;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/categories")
@AllArgsConstructor


public class CategorieController {
    private final IProduct_Services iProductServices;

    private final ICategorie_Service iCategorieService;
    @GetMapping("/All-Categorie")
    public List<CategoriesProduct> getAllcategories() {
        return iCategorieService.getAllcategories();
    }

    @PostMapping("/add-SubCategorie")
    public CategoriesProduct AddnewSubCategorie(@RequestBody CategoriesProduct subCat) {
        iCategorieService.AddSubnewCategorie(subCat);
        return subCat;
    }

    @GetMapping("/ParentCategories")
    public Set<CategoriesProduct> getParentcategories() {
        return iCategorieService.getParentcategories();
    }

    @PostMapping("/createCategoryWithSubcategories")
    public CategoriesProduct createCategoryWithSubcategories(@RequestBody CategoryCreationRequest request) {
        return iCategorieService.createCategoryWithSubcategories(
                request.getCategoryName(),
                request.getSubcategoryNames()
        );
    }

    @PostMapping("/{parentId}/addSubcategories")
    public String addSubcategoriesToParent(@PathVariable Integer parentId, @RequestBody List<String> subcategoryNames) {
            iCategorieService.addSubCategoriesToParent(parentId, subcategoryNames);
            return "Sous-catégories ajoutées avec succès à la catégorie parent.";

    }

    @GetMapping("/subCategories/{parentId}")
    public List<CategoriesProduct> getSubCategories(@PathVariable Integer parentId) {
        return iCategorieService.getSubCategories(parentId);
    }

    @GetMapping("/searchProduct/byCategory/{parentId}")
    public Set<Products> getProductsByParentCategory(@PathVariable Integer parentId) {
        return iProductServices.getProductsByParentCategory(parentId);
    }

    @GetMapping("/searchProduct/bySubcategory/{subCategoryId}")
    public Set<Products> getProductsBySubCategory(@PathVariable Integer subCategoryId) {
        return iProductServices.getProductsSubCategory(subCategoryId);
    }
}