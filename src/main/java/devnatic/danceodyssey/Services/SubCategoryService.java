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
        // Retrieve the parent category by its ID
        ParentCategory parentCategory = parentCategoryRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent category not found with id: " + parentId));

        // Associate the subcategory with the parent category
        subCategory.setParentCategory(parentCategory);

        // Save the subcategory
        return subCategoryRepository.save(subCategory);
    }


    @Override
    public SubCategory update(Integer id, SubCategory subCategory) {
        // Retrieve the existing subcategory by its ID
        SubCategory existingSubCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + id));

        // Update the fields of the existing subcategory
        existingSubCategory.setType(subCategory.getType());
        // You can update other fields here if needed

        // Save and return the updated subcategory
        return subCategoryRepository.save(existingSubCategory);
    }

    @Override
    public void delete(Integer id) {
        // Check if the subcategory exists
        if (!subCategoryRepository.existsById(id)) {
            throw new RuntimeException("Subcategory not found with id: " + id);
        }

        // Delete the subcategory by its ID
        subCategoryRepository.deleteById(id);
    }

    @Override
    public void deleteByParentCategoryId(Integer parentId, Integer subCategoryId) {
        // Retrieve the parent category by its ID
        ParentCategory parentCategory = parentCategoryRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent category not found with id: " + parentId));

        // Retrieve the subcategory by its ID
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + subCategoryId));

        // Check if the retrieved subcategory belongs to the specified parent category
        if (!subCategory.getParentCategory().getId().equals(parentCategory.getId())) {
            throw new RuntimeException("Subcategory with id " + subCategoryId + " does not belong to parent category with id " + parentId);
        }

        // Delete the subcategory
        subCategoryRepository.delete(subCategory);
    }
}
