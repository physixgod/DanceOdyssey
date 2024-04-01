package devnatic.danceodyssey.Interfaces;


import devnatic.danceodyssey.DAO.Entities.ParentCategory;
import devnatic.danceodyssey.DAO.Entities.SubCategory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IParentCategoryService {
    public void addParentCategoryWithSubCategories(ParentCategory parentCategory, List<SubCategory> subCategories) ;
    public List<ParentCategory> getAllParentCategories() ;


    }
