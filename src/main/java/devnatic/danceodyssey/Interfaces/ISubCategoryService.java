package devnatic.danceodyssey.Interfaces;

import devnatic.danceodyssey.DAO.Entities.SubCategory;

import java.util.List;

public interface ISubCategoryService {
    SubCategory add(SubCategory subCategory, Integer parentId);

    SubCategory update(Integer id, SubCategory subCategory);

    void delete(Integer id);
    public void deleteByParentCategoryId(Integer parentId, Integer subCategoryId) ;

    }
