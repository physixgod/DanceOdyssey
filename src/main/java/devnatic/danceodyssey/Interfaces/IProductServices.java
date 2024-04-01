package devnatic.danceodyssey.Interfaces;

import devnatic.danceodyssey.DAO.Entities.Image;
import devnatic.danceodyssey.DAO.Entities.Products;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IProductServices {


    public List<Products> ShowAllProducts();

    public void archiveProduct(Integer id) ;
    public Set<Products> getArchivedProducts(Boolean archived);
    public List<Products> getProduitsByIds(List<Integer> idProducts);
    public List<Image> getImagesForProduct(Integer productId) ;
    public Set<Products> searchByName(String name);
    public Products updateProductById(Integer productId, Products updatedProduct) ;
    public void unarchiveProduct(Integer id) ;


    public void addRatingToProduct(Integer ratingId, Integer productId, Integer userId) ;
    public List<Products> getLast5Products();
    List<Products> getTopRatingProducts();

    public List<Products> getPromotionalProducts();

    public Products addProduct(Products products, Integer parentId) ;
    public void addImagesToProduct(List<MultipartFile> imageFiles, int productId) throws IOException;
    public void updateImageForProduct(MultipartFile updatedImageFile, int productId, int imageId) throws IOException ;

}