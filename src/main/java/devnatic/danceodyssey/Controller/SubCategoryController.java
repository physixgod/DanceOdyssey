package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.DAO.Entities.SubCategory;
import devnatic.danceodyssey.Interfaces.ISubCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/subcategories")
@AllArgsConstructor
public class SubCategoryController {
    private  final ISubCategoryService  iSubCategoryService;


    @PostMapping("/{parentId}")
    public SubCategory addSubCategoryToParent(@RequestBody SubCategory subCategory, @PathVariable Integer parentId) {
        return iSubCategoryService.add(subCategory, parentId);
    }

    @PutMapping("/{id}")
    public SubCategory updateSubCategory(@PathVariable Integer id, @RequestBody SubCategory subCategory) {
        return iSubCategoryService.update(id, subCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteSubCategory(@PathVariable Integer id) {
        iSubCategoryService.delete(id);
    }
    @DeleteMapping("/parent-categories/{parentId}/subcategories/{subCategoryId}")
    public void deleteSubCategoryByParentCategoryId(@PathVariable Integer parentId, @PathVariable Integer subCategoryId) {
        iSubCategoryService.deleteByParentCategoryId(parentId, subCategoryId);
    }
    @GetMapping("gg")
    public String gg (){
        return "gg";
    }
}
