package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.ParentCategory;
import devnatic.danceodyssey.DAO.Entities.SubCategory;
import devnatic.danceodyssey.DAO.Repositories.ParentCategoryRepository;
import devnatic.danceodyssey.DAO.Repositories.SubCategoryRepository;
import devnatic.danceodyssey.Interfaces.IParentCategoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ParentCategoryService implements IParentCategoryService {
    private  final ParentCategoryRepository parentCategoryRepository;
    private  final SubCategoryRepository subCategoryRepository;
    @Transactional
    @Override
    public void addParentCategoryWithSubCategories(ParentCategory parentCategory, List<SubCategory> subCategories) {
        // Save the parent category first to generate its ID
        ParentCategory savedParentCategory = parentCategoryRepository.save(parentCategory);

        // Associate subcategories with the saved parent category
        for (SubCategory subCategory : subCategories) {
            // Set the parent category for each subcategory
            subCategory.setParentCategory(savedParentCategory);
            // Save each subcategory
            subCategoryRepository.save(subCategory);
        }

        log.info("Parent category with subcategories added successfully.");
    }

    @Override
    public List<ParentCategory> getAllcategories() {
        return parentCategoryRepository.findAll();
    }
}


