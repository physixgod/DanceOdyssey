package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.ImageData;
import devnatic.danceodyssey.DAO.Entities.Products;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IProduct_Services {
    public Products AddProduct(Products products);

    public Products updateProducts(Products pro);

    public List<Products> ShowAllProducts();

    public List<ImageData> getImagesForProduct(Integer idProduct) ;
    public ResponseEntity<String> archiveProduct(Integer id) ;
    public Products AddProductToCategory(Integer productId, Integer categoryId, List<Integer> subCategoryIds) ;
    public Products getProductByRefProduct(Integer refProduct) ;
    public Set<Products> getArchivedProducts(Boolean archived);
    public List<Products> getProduitsByIds(List<Integer> idProducts);


}