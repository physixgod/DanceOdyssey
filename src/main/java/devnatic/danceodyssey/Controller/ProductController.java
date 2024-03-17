package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.DAO.Entities.CategoriesProduct;
import devnatic.danceodyssey.DAO.Entities.CategoryCreationRequest;
import devnatic.danceodyssey.DAO.Entities.Image;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Repositories.ImageRepository;
import devnatic.danceodyssey.DAO.Repositories.ProductRepository;
import devnatic.danceodyssey.Services.CloudinaryService;
import devnatic.danceodyssey.Services.ICategorie_Service;
import devnatic.danceodyssey.Services.IProduct_Services;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final IProduct_Services iProductServices;

    private final ICategorie_Service iCategorieService;


//Product

    @PostMapping("/AddProduct")
    public Products AddProduct(@RequestBody Products products) {
        return iProductServices.AddProduct(products);
    }


    @GetMapping("/ShowAllProducts")
    public List<Products> ShowAllProducts() {
        return iProductServices.ShowAllProducts();
    }

    @PutMapping("/updateProducts")
    public Products updateProducts(@RequestBody Products pro) {
        return iProductServices.updateProducts(pro);
    }


    @PutMapping("/archiveProduct/{id}")
    public ResponseEntity<String> archiveProduct(@PathVariable Integer id) {
        ResponseEntity<String> archiveResponse = iProductServices.archiveProduct(id);

        if (archiveResponse != null && archiveResponse.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Product archived successfully");
        } else {
            return ResponseEntity.status(archiveResponse != null ? archiveResponse.getStatusCode() : HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(archiveResponse != null ? archiveResponse.getBody() : "Failed to archive product");
        }
    }

    @PostMapping("/add-to-category/{productId}/{categoryId}/{subCategoryId}")
    public ResponseEntity<String> addProductToCategory(@PathVariable Integer productId,
                                                       @PathVariable Integer categoryId,
                                                       @PathVariable Integer subCategoryId) {
        try {
            System.out.println("Received request to add product to category. Product ID: " + productId + ", Category ID: " + categoryId + ", SubCategory ID: " + subCategoryId);

            // Appeler le service pour associer le produit à la catégorie principale et à la sous-catégorie
            Products product = iProductServices.AddProductToCategory(productId, categoryId, Collections.singletonList(subCategoryId));

            return ResponseEntity.ok("Product added to category successfully. Product ID: " + product.getIdProduct());
        } catch (Exception e) {
            System.err.println("Error adding product to category: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product to category: " + e.getMessage());
        }
    }

    @GetMapping("/byRefProduct/{refProduct}")
    public ResponseEntity<Products> getProductByRefProduct(@PathVariable Integer refProduct) {
        Products product = iProductServices.getProductByRefProduct(refProduct);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @GetMapping("/archived")
    public Set<Products> getArchivedProducts() {
        return iProductServices.getArchivedProducts(true);
    }

    @PostMapping("/add-SubCategorie")
    public CategoriesProduct AddnewSubCategorie(@RequestBody CategoriesProduct subCat) {
        iCategorieService.AddSubnewCategorie(subCat);
        return subCat;
    }

    @GetMapping("/All-Categorie")
    public List<CategoriesProduct> getAllcategories() {
        return iCategorieService.getAllcategories();
    }

    @GetMapping("/ParentCategories")
    public Set<CategoriesProduct> getParentcategories() {
        return iCategorieService.getParentcategories();
    }

    @PostMapping("/createCategoryWithSubcategories")
    public ResponseEntity<CategoriesProduct> createCategoryWithSubcategories(@RequestBody CategoryCreationRequest request) {
        CategoriesProduct createdCategory = iCategorieService.createCategoryWithSubcategories(
                request.getCategoryName(),
                request.getSubcategoryNames()
        );

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/subCategories/{parentId}")
    public List<CategoriesProduct> getSubCategories(@PathVariable Integer parentId) {
        return iCategorieService.getSubCategories(parentId);
    }

    @GetMapping("/{idProducts}")
    public List<Products> getProduitsByIds(@PathVariable Integer idProducts) {
        List<Integer> idList = Collections.singletonList(idProducts);
        return iProductServices.getProduitsByIds(idList);
    }



    @PostMapping(value = "/{productId}/addImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addImagesToProduct(
            @RequestParam("imageFiles") List<MultipartFile> imageFiles,
            @PathVariable("productId") int productId) {
        try {
            iProductServices.addImagesToProduct(imageFiles, productId);
            return ResponseEntity.ok("Images ajoutées avec succès");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du téléchargement des images");
        }
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
    public ResponseEntity<String> updateProductImage(
            @PathVariable int productId,
            @PathVariable int imageId,
            @RequestParam("imageFile") MultipartFile updatedImageFile) {

        try {
            iProductServices.updateImageForProduct(updatedImageFile, productId, imageId);
            return ResponseEntity.ok("Image updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating image");
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Products> updateProductById(@PathVariable Integer productId, @RequestBody Products updatedProduct) {
        try {
            Products updated = iProductServices.updateProductById(productId, updatedProduct);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Handle the case where the product is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}




