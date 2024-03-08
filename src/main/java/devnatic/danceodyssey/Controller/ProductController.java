package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.DAO.Entities.CategoriesProduct;
import devnatic.danceodyssey.DAO.Entities.CategoryCreationRequest;
import devnatic.danceodyssey.DAO.Entities.ImageData;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Repositories.ImageRepository;
import devnatic.danceodyssey.Services.ICategorie_Service;
import devnatic.danceodyssey.Services.IProduct_Services;
import devnatic.danceodyssey.Services.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final IProduct_Services iProductServices;

    private  final ICategorie_Service iCategorieService;

    @Autowired
    StorageService storageService;

//Product

    @PostMapping("AddProduct")
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


    @PostMapping(value = "/uploadImage/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Integer id) throws IOException {
        ResponseEntity<String> uploadImageResponse = storageService.uploadImage(file, id);

        // Vérifiez si le téléchargement de l'image a réussi
        if (uploadImageResponse != null && uploadImageResponse.getStatusCode() == HttpStatus.OK) {
            // Retournez l'URL complète de l'image
            String imageUrl = "http://localhost:8086/DanceOdyssey/products/getProductImage/" + id;
            return ResponseEntity.ok(imageUrl);
        } else {
            // En cas d'échec, retournez le message d'erreur
            return ResponseEntity.status(uploadImageResponse != null ? uploadImageResponse.getStatusCode() : HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(uploadImageResponse != null ? uploadImageResponse.getBody() : "Image upload failed");
        }
    }

    @PutMapping(value = "/updateImage/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateImage(@RequestParam("image") MultipartFile file, @PathVariable Integer id) throws IOException {
        ResponseEntity<String> updateImageResponse = storageService.updateImage(file, id);

        // Check if the image update was successful
        if (updateImageResponse != null && updateImageResponse.getStatusCode() == HttpStatus.OK) {
            // Return the complete URL of the updated image
            String imageUrl = "http://localhost:8086/DanceOdyssey/products/getProductImage/" + id;
            return ResponseEntity.ok(imageUrl);
        } else {
            // In case of failure, return the error message
            return ResponseEntity.status(updateImageResponse != null ? updateImageResponse.getStatusCode() : HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(updateImageResponse != null ? updateImageResponse.getBody() : "Image update failed");
        }
    }



    @Autowired
    ImageRepository imageRepository;

    @GetMapping(value = "/produits/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getImageForProduit(@PathVariable Integer id) {
        try {
            System.out.println("Demande d'image pour le produit avec l'ID : " + id);

            List<ImageData> imageList = imageRepository.findByProduit_IdProduct(id);

            if (imageList.isEmpty()) {
                System.out.println("Aucune image trouvée pour le produit avec l'ID : " + id);
                return ResponseEntity.notFound().build();
            }

            ImageData imageData = imageList.get(0);
            byte[] imageBytes = devnatic.danceodyssey.util.ImageUtils.decompressImage(imageData.getImageData());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            System.out.println("Image trouvée pour le produit avec l'ID : " + id);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Ajoutez un log pour capturer les exceptions possibles lors de la récupération de l'image
            System.err.println("Erreur lors de la récupération de l'image : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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


}


