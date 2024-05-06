package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.DAO.Entities.*;
import devnatic.danceodyssey.DAO.Repositories.RaitingProductRepository;
import devnatic.danceodyssey.DAO.Repositories.UserRepo;
import devnatic.danceodyssey.Interfaces.IOrderLineService;
import devnatic.danceodyssey.Interfaces.IProductServices;
import devnatic.danceodyssey.Interfaces.IRaitingProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private  final IOrderLineService iOrderService;

    private   final RaitingProductRepository raitingProductRepository;

    private final IProductServices iProductServices;
private  final IRaitingProductService iRaitingProductService;
    @PostMapping("/{parentId}/product/{subCategoryId}")
    public Products addProduct(@PathVariable Integer parentId,
                               @PathVariable Integer subCategoryId,
                               @RequestBody Products products) {
        return iProductServices.addProduct(products, parentId, subCategoryId);
    }


    @PostMapping(value = "/{productId}/addImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addImagesToProduct(
            @RequestParam("mediaFiles") List<MultipartFile> mediaFiles,
            @PathVariable("productId") int productId) throws IOException {
        iProductServices.addMediaToProduct(mediaFiles, productId);
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
    public void unarchiveProduct(@PathVariable Integer id) {
        iProductServices.unarchiveProduct(id);
    }

    @GetMapping("/{productId}/images")
    public List<MediaFiles> getImagesForProduct(@PathVariable Integer productId) {
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
    @GetMapping("/products/top-10Order")
    public List<Products> getTop10_Order_Product() {
        return iProductServices.getTop10OrderProduct();
    }


    @PostMapping("/ajouterRainting/{productId}/{userId}")
    public void AddRaitingToProduit(@RequestBody RatingProduct rating, @PathVariable Integer productId, @PathVariable Long userId) {
        RatingProduct R =  iRaitingProductService.addRaiting(rating);
System.err.println("+++++++++++++++++++++++++++++++++++++++++++" + R.getScore());





        iProductServices.addRatingToProduct(R.getId(), productId, userId);
    }

    @PutMapping(value = "/{productId}/media/{mediaId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> updateMediaForProduct(
            @RequestParam("files") List<MultipartFile> newMediaFiles,
            @PathVariable("productId") int productId,
            @PathVariable("mediaId") int mediaId) throws IOException {
        return iProductServices.updateMediaForProduct(newMediaFiles, productId, mediaId);
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


    @GetMapping("/SearchByParentByNameproducts")
    public List<Products> getProductsByParentCategoryAndProductName(
            @RequestParam("parentCategoryId") Integer parentCategoryId,
            @RequestParam("productName") String productName
    ) {
        return iProductServices.getProductsByParentCategoryAndProductName(parentCategoryId, productName);
    }

    @GetMapping("/{productId}/reactions")
    public Set<RatingProduct> getProductReactions(@PathVariable("productId") Integer productId) {
        return iProductServices.getProductReactions(productId);
    }
    @GetMapping("/subcategories/{subCategoryId}/products")
    public List<Products> getProductsBySubCategoryId(@PathVariable Integer subCategoryId) {
        return iProductServices.getProductsBySubCategoryId(subCategoryId);
    }
    @GetMapping("/top5ProductVendu/{parentCategoryId}")
    public List<Products> getTop5ProductsByParentCategoryId(@PathVariable Integer parentCategoryId) {
        return iProductServices.findTop5ProductsByParentCategoryId(parentCategoryId);
    }
    @GetMapping("/top5Promotional/{parentCategoryId}")
    public List<Products> getTop5ProductsPromotionalByParentCategoryId(@PathVariable Integer parentCategoryId) {
        return iProductServices.findTop5ProductsIspromotion(parentCategoryId);
    }
    @GetMapping("/Last5Product_Parent/{parentCategoryId}")
    public List<Products> getLastProduct_ParenByParentCategoryId(@PathVariable Integer parentCategoryId) {
        return iProductServices.getLast5ProductsByParentCategoryId(parentCategoryId);
    }
    @GetMapping("/AvreageScoreProduct_Parent/{parentCategoryId}")
    public List<Products> AvreageScoreProduct_ByParentCategoryId(@PathVariable Integer parentCategoryId) {
        return iProductServices.getAvreagescoreProductsByParentCategoryId(parentCategoryId);
    }
    UserRepo userRepo;
    @GetMapping("getCartID/{idUser}")
    public int getCartID(@PathVariable("idUser")Long idUser){
        User user=userRepo.findById(idUser).get();
        return user.getCart().getId();
    }

}











