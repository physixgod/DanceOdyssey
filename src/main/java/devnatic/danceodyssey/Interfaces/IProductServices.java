package devnatic.danceodyssey.Interfaces;

import devnatic.danceodyssey.DAO.Entities.MediaFiles;
import devnatic.danceodyssey.DAO.Entities.ParentCategory;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Entities.RatingProduct;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IProductServices {


    public List<Products> ShowAllProducts();

    public void archiveProduct(Integer id) ;
    public Set<Products> getArchivedProducts(Boolean archived);
    public List<Products> getProduitsByIds(List<Integer> idProducts);
    public List<MediaFiles> getImagesForProduct(Integer productId) ;
    public Set<Products> searchByName(String name);
    public Products updateProductById(Integer productId, Products updatedProduct) ;
    public void unarchiveProduct(Integer id) ;


    public void addRatingToProduct(Integer ratingId, Integer productId, Long userId) ;
    public List<Products> getLast5Products();
    public List<Products> getTop10OrderProduct();

    List<Products> getTopRatingProducts();

    public List<Products> getPromotionalProducts();

    public Products addProduct(Products products, Integer parentId,Integer subCategoryId ) ;
    public void addMediaToProduct(List<MultipartFile> mediaFiles, int productId) throws IOException ;
    public List<Products> getProductsByParentCategory(ParentCategory parentCategory) ;


    public List<Products> getProductsByParentCategoryId(Integer parentId) ;
    List<Products> getProductsByParentCategoryAndProductName(Integer parentCategoryId, String productName);
    public Set<RatingProduct> getProductReactions(Integer productId);
    public List<Products> getProductsBySubCategoryId(Integer subCategoryId);
    public List<Products> findTop5ProductsByParentCategoryId(Integer parentCategoryId) ;
    public List<Products> findTop5ProductsIspromotion(Integer parentCategoryId) ;
    List<Products> getLast5ProductsByParentCategoryId(Integer parentCategoryId);
    List<Products> getAvreagescoreProductsByParentCategoryId(Integer parentCategoryId);
    public List<String> updateMediaForProduct(List<MultipartFile> newMediaFiles, int productId, int mediaId) throws IOException ;

}