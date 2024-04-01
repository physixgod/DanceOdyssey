package devnatic.danceodyssey.Interfaces;

import devnatic.danceodyssey.DAO.Entities.Image;
import devnatic.danceodyssey.DAO.Entities.Products;

import java.util.List;
import java.util.Set;

public interface IProductServices {


    public List<Products> ShowAllProducts();

    public void archiveProduct(Integer id) ;
    public Set<Products> getArchivedProducts(Boolean archived);
    public Products getProductById(Integer idProduct);
    public List<Image> getImagesForProduct(Integer productId) ;
    public Set<Products> searchByName(String name);
    public Products updateProductById(Integer productId, Products updatedProduct) ;
    public void unarchiveProduct(Integer id) ;


    public void addRatingToProduct(Integer ratingId, Integer productId, Integer userId) ;
    public List<Products> getLast5Products();
    List<Products> getTopRatingProducts();

    public List<Products> getPromotionalProducts();

    Products addProduct(Products products,Integer parentId);

}