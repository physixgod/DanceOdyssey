package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.DAO.Entities.Image;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Entities.RatingProducts;
import devnatic.danceodyssey.Services.CloudinaryService;
import devnatic.danceodyssey.Services.IProduct_Services;
import devnatic.danceodyssey.Services.IRaitingProduct;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final IProduct_Services iProductServices;
    private  final IRaitingProduct iRaitingProduct;


@PostMapping("/AddProduct")
public Products AddProduct(@RequestBody Products products) {
    return iProductServices.AddProduct(products);
}

@PutMapping("/updateProducts")
public Products updateProducts(@RequestBody Products pro) {
    return iProductServices.updateProducts(pro);
}
    @PutMapping("/{productId}")
    public Products updateProductById(@PathVariable Integer productId, @RequestBody Products updatedProduct) {
        return iProductServices.updateProductById(productId, updatedProduct);

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
    public void  unarchiveProduct(@PathVariable Integer id) {
           iProductServices.unarchiveProduct(id);


    }

@GetMapping("/ShowAllProducts")
public List<Products> ShowAllProducts() {
    return iProductServices.ShowAllProducts();
}
    @GetMapping("/Search_ProductsName/CategorieParent")
public List<Products> searchProductsByNameAndCategory(@RequestParam String name, @RequestParam Integer categoryId) {
    return iProductServices.searchProductsByNameAndCategory(name, categoryId);
}
    @GetMapping("/SearchName_Products/SubCategories")
      public List<Products> searchProductsByNameAndSubCategory(String name, Integer subCategoryId) {

        return iProductServices.searchProductsByNameAndSubCategory(name, subCategoryId);
    }

    @PostMapping("/add-to-category/{productId}/{categoryId}/{subCategoryId}")
    public void addProductToCategory(@PathVariable Integer productId,
                                     @PathVariable Integer categoryId,
                                     @PathVariable Integer subCategoryId) {
        iProductServices.AddProductToCategory(productId, categoryId, Collections.singletonList(subCategoryId));
    }


    @GetMapping("/byRefProduct/{refProduct}")
    public void getProductByRefProduct(@PathVariable Integer refProduct) {
   iProductServices.getProductByRefProduct(refProduct);
    }


    @GetMapping("/{idProducts}")
    public List<Products> getProduitsByIds(@PathVariable Integer idProducts) {
        List<Integer> idList = Collections.singletonList(idProducts);
        return iProductServices.getProduitsByIds(idList);
    }


    @PostMapping(value = "/{productId}/addImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addImagesToProduct(
            @RequestParam("imageFiles") List<MultipartFile> imageFiles,
            @PathVariable("productId") int productId) throws IOException {
        iProductServices.addImagesToProduct(imageFiles, productId);
    }

    @GetMapping("/{productId}/images")
    public List<Image> getImagesForProduct(@PathVariable Integer productId) {
        return iProductServices.getImagesForProduct(productId);
    }

    @GetMapping("/search/{name}")
    public Set<Products> searchByName(@PathVariable("name")String name){
        return iProductServices.searchByName(name);
    }

    @Autowired
    CloudinaryService cloudinaryService;
    @PutMapping(value = "/{productId}/images/{imageId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void  updateProductImage(
            @PathVariable int productId,
            @PathVariable int imageId,
            @RequestParam("imageFile") MultipartFile updatedImageFile) throws EntityNotFoundException, IOException {

        iProductServices.updateImageForProduct(updatedImageFile, productId, imageId);
    }

    @PostMapping("/ajouterRainting/{productId}/{userId}")
    public void AddRaitingToProduit(@RequestBody RatingProducts ratingP, @PathVariable Integer productId, @PathVariable Integer userId) {

        iRaitingProduct.AddNEwRaitingProduit(ratingP);
        iProductServices.AddRatingToProduct(ratingP.getId(), productId, userId);
    }
    @GetMapping("/last-5")
    public List<Products> getLast5Products() {
        return iProductServices.getLast5Products();
    }

    @GetMapping("/SumRaint")
    public String sumProduit (Integer idproduit,  Integer idporduit2 )
    {
        return iProductServices.SumRatting(idproduit,idporduit2) ;
    }

    @PutMapping("/calculate-reduced-price/{productId}/{promotionPercentage}")
    public Float calculateReducedPrice(@PathVariable Integer productId, @PathVariable Integer promotionPercentage) {
        return iProductServices.calculateReducedPrice(productId, promotionPercentage);
    }
    @GetMapping("/promotions")
    public List<Products> getPromotionalProducts() {
        return iProductServices.getPromotionalProducts();
    }
    @GetMapping("/products/top-rating")
    public List<Products> getTopRatingProducts() {
        return iProductServices.getTopRatingProducts();
    }
    }








