package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Image;
import devnatic.danceodyssey.DAO.Entities.Products;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IProduct_Services {
    public Products AddProduct(Products products);

    public Products updateProducts(Products pro);

    public List<Products> ShowAllProducts();

    public void archiveProduct(Integer id) ;
    public void AddProductToCategory(Integer productId, Integer categoryId, List<Integer> subCategoryIds) ;
    public void getProductByRefProduct(Integer refProduct) ;
    public Set<Products> getArchivedProducts(Boolean archived);
    public List<Products> getProduitsByIds(List<Integer> idProducts);
    public void addImagesToProduct(List<MultipartFile> imageFiles, int productId) throws IOException ;
    public List<Image> getImagesForProduct(Integer productId) ;
    public Set<Products> searchByName(String name);
    public void updateImageForProduct(MultipartFile updatedImageFile, int productId, int imageId) throws IOException ;
    public Products updateProductById(Integer productId, Products updatedProduct) ;
    public void unarchiveProduct(Integer id) ;
    public Set<Products> getProductsByParentCategory(Integer parentId) ;

    public Set<Products> getProductsSubCategory(Integer subCategoryId) ;
    public List<Products> searchProductsByNameAndCategory(String name, Integer categoryId) ;

    public List<Products> searchProductsByNameAndSubCategory(String name, Integer subCategoryId) ;

    }