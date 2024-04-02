package devnatic.danceodyssey.Services;


import devnatic.danceodyssey.DAO.Entities.ParentCategory;
import devnatic.danceodyssey.DAO.Entities.SubCategory;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IParentCategoryService {
    public void addParentCategoryWithSubCategories(ParentCategory parentCategory, List<SubCategory> subCategories) ;
    public List<ParentCategory> getAllParentCategories() ;
public ParentCategory getParentCategorybyType(String type);

    }
