package devnatic.danceodyssey.Interfaces;


import devnatic.danceodyssey.DAO.Entities.ParentCategory;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Entities.SubCategory;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IParentCategoryService {
    public ParentCategory  addParentCategoryWithSubCategories(ParentCategory parentCategory, List<SubCategory> subCategories) ;
    public List<ParentCategory> getAllParentCategories() ;
    public ParentCategory getParentCategorybyType(String type);
    public void uploadImageToCategorie(MultipartFile file,Integer id) throws IOException ;
    public void updateParentCategoryWithImage(Integer id, MultipartFile file) throws IOException ;
    public String getParentCategoryImage(Integer id) ;
    public ParentCategory deleteParentCategory(Integer parentId);
    }
