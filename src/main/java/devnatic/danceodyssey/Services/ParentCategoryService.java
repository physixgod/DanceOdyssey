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
        ParentCategory savedParentCategory = parentCategoryRepository.save(parentCategory);

        for (SubCategory subCategory : subCategories) {
            subCategory.setParentCategory(savedParentCategory);
            subCategoryRepository.save(subCategory);
        }

        log.info("Parent category with subcategories added successfully.");
    }

    @Override
    public List<ParentCategory> getAllcategories() {
        return parentCategoryRepository.findAll();
    }
}


