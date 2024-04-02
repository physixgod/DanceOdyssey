package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.DAO.Entities.Image;
import devnatic.danceodyssey.DAO.Entities.ParentCategory;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Entities.RatingProducts;
import devnatic.danceodyssey.Services.IProductServices;
import devnatic.danceodyssey.Services.IRaitingProduct;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final IProductServices iProductServices;
    private final IRaitingProduct iRaitingProduct;
    @PostMapping("/{parentId}/product")
    public Products addProduct( @PathVariable Integer parentId,@RequestBody Products products){
        return iProductServices.addProduct(products,parentId);
    }
    @PostMapping(value = "/{productId}/addImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addImagesToProduct(
            @RequestParam("imageFiles") List<MultipartFile> imageFiles,
            @PathVariable("productId") int productId) throws IOException {
        iProductServices.addImagesToProduct(imageFiles, productId);
    }

    @PutMapping("/{productId}")
    public Products updateProductById(@PathVariable Integer productId, @RequestBody Products updatedProduct) {
        return iProductServices.updateProductById(productId, updatedProduct);
    }

    @GetMapping("/{idProducts}")
    public List<Products> getProduitsByIds(@PathVariable Integer idProducts) {
        List<Integer> idList = Collections.singletonList(idProducts);
        return iProductServices.getProduitsByIds(idList);
    }

    @GetMapping("/ShowAllProducts")
    public List<Products> ShowAllProducts() {
        return iProductServices.ShowAllProducts();
    }

    @GetMapping("/archived")
    public Set<Products> getArchivedProducts() {
        return iProductServices.getArchivedProducts(true);
    }
    @PutMapping("/archiveProduct/{id}")
    public void archiveProduct(@PathVariable Integer id) {
        iProductServices.archiveProduct(id);
    }

    @PutMapping("/unarchiveProduct/{id}")
    public void  unarchiveProduct(Integer id) {
        iProductServices.unarchiveProduct(id);


    }

    @GetMapping("/{productId}/images")
    public List<Image> getImagesForProduct(@PathVariable Integer productId) {
        return iProductServices.getImagesForProduct(productId);
    }
    @GetMapping("/last-5")
    public List<Products> getLast5Products() {
        return iProductServices.getLast5Products();
    }

    @GetMapping("/promotions")
    public List<Products> getPromotionalProducts() {
        return iProductServices.getPromotionalProducts();
    }
    @GetMapping("/search/{name}")
    public Set<Products> searchByName(@PathVariable("name") String name) {
        return iProductServices.searchByName(name);
    }

    @GetMapping("/products/top-rating")
    public List<Products> getTopRatingProducts() {
        return iProductServices.getTopRatingProducts();
    }


    @PostMapping("/ajouterRainting/{productId}/{userId}")
    public void AddRaitingToProduit(@RequestBody RatingProducts ratingP, @PathVariable Integer productId, @PathVariable Integer userId) {

        iRaitingProduct.addNEwRaitingProduit(ratingP);
        iProductServices.addRatingToProduct(ratingP.getId(), productId, userId);
    }
    @PutMapping(value = "/{productId}/images/{imageId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void  updateProductImage(
            @PathVariable int productId,
            @PathVariable int imageId,
            @RequestParam("imageFile") MultipartFile updatedImageFile) throws EntityNotFoundException, IOException {

        iProductServices.updateImageForProduct(updatedImageFile, productId, imageId);
    }
    @GetMapping("/byParentCategory/{parentId}")
    public List<Products> getProductsByParentCategory(@PathVariable Integer parentId) {
        ParentCategory parentCategory = new ParentCategory();
        parentCategory.setId(parentId);
        return iProductServices.getProductsByParentCategory(parentCategory);
    }
    @GetMapping("/byParent/{parentId}")
    public List<Products> getProductsByParentCategoryId(@PathVariable Integer parentId) {
        return iProductServices.getProductsByParentCategoryId(parentId);
    }
}








