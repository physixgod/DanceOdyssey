package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.ParentCategory;
import devnatic.danceodyssey.DAO.Entities.SubCategory;
import devnatic.danceodyssey.DAO.Repositories.ParentCategoryRepository;
import devnatic.danceodyssey.DAO.Repositories.SubCategoryRepository;
import devnatic.danceodyssey.Interfaces.IParentCategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ParentCategoryService implements IParentCategoryService {
    private  final ParentCategoryRepository parentCategoryRepository;
    private  final SubCategoryRepository subCategoryRepository;
    private  final  CloudinaryService cloudinaryService;
    @Transactional
    @Override
    public ParentCategory  addParentCategoryWithSubCategories(ParentCategory parentCategory, List<SubCategory> subCategories) {
        ParentCategory savedParentCategory = parentCategoryRepository.save(parentCategory);

        for (SubCategory subCategory : subCategories) {
            subCategory.setParentCategory(savedParentCategory);
            subCategoryRepository.save(subCategory);
        }

        log.info("Parent category with subcategories added successfully.");
        return savedParentCategory; // Retourner l'objet ParentCategory nouvellement créé
    }



    @Override
    public List<ParentCategory> getAllParentCategories() {
        return parentCategoryRepository.findAll();
    }

    @Override
    public ParentCategory getParentCategorybyType(String type) {
        return parentCategoryRepository.findByType(type);
    }

    @Override
    public void uploadImageToCategorie(MultipartFile file, Integer id) throws IOException {
        String imgUrl = cloudinaryService.uploadSingleImageToCloudinary(file, id);
        Optional<ParentCategory> optionalParentCategory = parentCategoryRepository.findById(id);
        if (optionalParentCategory.isPresent()) {
            ParentCategory parentCategory = optionalParentCategory.get();
            parentCategory.setImgUrl(imgUrl);
            parentCategoryRepository.save(parentCategory);
        } else {
            throw new IllegalArgumentException("ParentCategories not found with ID: " + id);
        }
    }

    @Override
    public void updateParentCategoryWithImage(Integer id, MultipartFile file) throws IOException {
        Optional<ParentCategory> optionalParentCategory = parentCategoryRepository.findById(id);
        if (optionalParentCategory.isPresent()) {
            ParentCategory parentCategory = optionalParentCategory.get();

            // Upload new image to Cloudinary
            String imgUrl = cloudinaryService.uploadSingleImageToCloudinary(file, id);
            // Update imgUrl of parentCategory
            parentCategory.setImgUrl(imgUrl);

            // Save the updated parentCategory
            parentCategoryRepository.save(parentCategory);
        } else {
            throw new IllegalArgumentException("ParentCategory not found with ID: " + id);
        }
    }

    @Override
    public String getParentCategoryImage(Integer id) {
        // Rechercher la catégorie parent par ID
        ParentCategory parentCategory = parentCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ParentCategory not found with ID: " + id));

        // Retourner l'URL de l'image de la catégorie parent
        return parentCategory.getImgUrl();
    }

    @Override
    public ParentCategory deleteParentCategory(Integer parentId) {
            ParentCategory parentCategory = parentCategoryRepository.findById(parentId)
                    .orElseThrow(() -> new EntityNotFoundException("Parent category not found with id: " + parentId));

            parentCategoryRepository.deleteById(parentId);
            return parentCategory;
        }



    }




