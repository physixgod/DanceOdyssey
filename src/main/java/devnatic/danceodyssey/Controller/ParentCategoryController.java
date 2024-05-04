package devnatic.danceodyssey.Controller;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import devnatic.danceodyssey.DAO.Entities.ParentCategory;
import devnatic.danceodyssey.DAO.Entities.SubCategory;
import devnatic.danceodyssey.Interfaces.IParentCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/parentcategories")
@AllArgsConstructor
@Slf4j
public class ParentCategoryController {
    private  final IParentCategoryService iParentCategoryService;
    @PostMapping("/addCategory")
    public ParentCategory addParentCategoryWithSubCategories(@Valid @RequestBody ParentCategory parentCategory) {
        List<SubCategory> subCategories = parentCategory.getSubCategories();
        // Appeler le service pour ajouter la catégorie parent avec les sous-catégories
        return iParentCategoryService.addParentCategoryWithSubCategories(parentCategory, subCategories);
    }

    @GetMapping("/parentcategories-with-subcategories")
    public List<ParentCategory> getAllParentCategoriesWithSubCategories() {
        return iParentCategoryService.getAllParentCategories();
    }
    @GetMapping("/parentcategories-with-type")
    public ParentCategory getParentCategorybyType( @PathVariable String type) {
        return iParentCategoryService.getParentCategorybyType(type);
    }

        @PostMapping(value = "/{id}/uploadimg",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadimgeToCategories(
            @RequestParam("imageFile") MultipartFile imageFile,
            @PathVariable("id") int id) throws IOException {
        iParentCategoryService.uploadImageToCategorie(imageFile, id);
        return "img uploaded successfully";
    }
    @PutMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateParentCategoryWithImage(@PathVariable("id") Integer id,
                                                @RequestParam("file") MultipartFile file) throws IOException {
        iParentCategoryService.updateParentCategoryWithImage(id, file);
        return "ParentCategory updated with image successfully.";
    }
    @GetMapping("/{id}/image")
    public String getParentCategoryImage(@PathVariable("id") Integer id) {
        return iParentCategoryService.getParentCategoryImage(id);
    }
    @DeleteMapping("/parent-categories/{parentId}/")
    public void deleteSubCategoryByParentCategoryId(@PathVariable Integer parentId) {
        iParentCategoryService.deleteParentCategory(parentId);
    }

}