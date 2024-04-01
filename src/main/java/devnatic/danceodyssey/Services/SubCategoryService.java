package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.ParentCategory;
import devnatic.danceodyssey.DAO.Entities.SubCategory;
import devnatic.danceodyssey.DAO.Repositories.ParentCategoryRepository;
import devnatic.danceodyssey.DAO.Repositories.SubCategoryRepository;
import devnatic.danceodyssey.Interfaces.ISubCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubCategoryService implements ISubCategoryService {
    private  final ParentCategoryRepository parentCategoryRepository;
    private  final SubCategoryRepository subCategoryRepository;


    @Override
    public SubCategory add(SubCategory subCategory, Integer parentId) {
        ParentCategory parentCategory = parentCategoryRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent category not found with id: " + parentId));

        subCategory.setParentCategory(parentCategory);

        return subCategoryRepository.save(subCategory);
    }


    @Override
    public SubCategory update(Integer id, SubCategory subCategory) {
        SubCategory existingSubCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + id));

        existingSubCategory.setType(subCategory.getType());

        return subCategoryRepository.save(existingSubCategory);
    }

    @Override
    public void delete(Integer id) {
        if (!subCategoryRepository.existsById(id)) {
            throw new RuntimeException("Subcategory not found with id: " + id);
        }

        subCategoryRepository.deleteById(id);
    }

    @Override
    public void deleteByParentCategoryId(Integer parentId, Integer subCategoryId) {
        ParentCategory parentCategory = parentCategoryRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent category not found with id: " + parentId));

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + subCategoryId));

        if (!subCategory.getParentCategory().getId().equals(parentCategory.getId())) {
            throw new RuntimeException("Subcategory with id " + subCategoryId + " does not belong to parent category with id " + parentId);
        }

        subCategoryRepository.delete(subCategory);
    }
}
