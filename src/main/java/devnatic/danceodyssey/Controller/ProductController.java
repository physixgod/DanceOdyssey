package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.DAO.Entities.Image;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Entities.RatingProducts;
import devnatic.danceodyssey.Interfaces.IProductServices;
import devnatic.danceodyssey.Interfaces.IRaitingProduct;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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


    @PutMapping("/{productId}")
    public Products updateProductById(@PathVariable Integer productId, @RequestBody Products updatedProduct) {
        return iProductServices.updateProductById(productId, updatedProduct);
    }
    @GetMapping("/{idProduct}")
    public Products getProductById(@PathVariable Integer idProduct) {
        return iProductServices.getProductById(idProduct);
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
}








