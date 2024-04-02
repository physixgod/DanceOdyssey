package devnatic.danceodyssey.Controller;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import devnatic.danceodyssey.DAO.Entities.ParentCategory;
import devnatic.danceodyssey.DAO.Entities.SubCategory;
import devnatic.danceodyssey.Services.IParentCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/parentcategories")
@AllArgsConstructor
@Slf4j
public class ParentCategoryController {
    private  final IParentCategoryService iParentCategoryService;
    @PostMapping("/addCategory")
    public ResponseEntity<String> addParentCategoryWithSubCategories(@Valid @RequestBody ParentCategory parentCategory) {
        try {
            List<SubCategory> subCategories = parentCategory.getSubCategories();

            // Appeler le service pour ajouter la catégorie parent avec les sous-catégories
            iParentCategoryService.addParentCategoryWithSubCategories(parentCategory, subCategories);

            log.info("Parent category with subcategories added successfully.");
            return ResponseEntity.ok("Parent category with subcategories added successfully.");
        } catch (Exception e) {
            log.error("Error occurred while adding parent category with subcategories: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding parent category with subcategories.");
        }

    }
    @GetMapping("/parentcategories-with-subcategories")
    public List<ParentCategory> getAllParentCategoriesWithSubCategories() {
        return iParentCategoryService.getAllParentCategories();
    }
    @GetMapping("/parentcategories-with-type")
    public ParentCategory getParentCategorybyType( @PathVariable String type) {
        return iParentCategoryService.getParentCategorybyType(type);
    }
}