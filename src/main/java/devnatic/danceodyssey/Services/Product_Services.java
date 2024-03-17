package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.CategoriesProduct;
import devnatic.danceodyssey.DAO.Entities.Image;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Repositories.CategoriesProductRepository;
import devnatic.danceodyssey.DAO.Repositories.ImageRepository;
import devnatic.danceodyssey.DAO.Repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class Product_Services implements IProduct_Services {
    private final ProductRepository productRepository;
    private final CategoriesProductRepository categoriesProductRepository;
    private  final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;



    @Override
    public Products AddProduct(Products products) {
        products.setRefProduct(generateNextRefProduct());

        if (productRepository.existsByRefProduct(products.getRefProduct())) {
            throw new RuntimeException("Product reference already exists.");
        }

        products.setArchived(false);

        return productRepository.save(products);
    }
    @Override
    public Products updateProducts(Products pro) {
        return productRepository.save(pro);
    }

    @Override
    public List<Products> ShowAllProducts() {
        return productRepository.findAll();
    }


    private Integer generateNextRefProduct() {
        Integer lastProductId = productRepository.findMaxProductId();

        if (lastProductId != null) {
            return lastProductId + 1;
        } else {
            return 1000;
        }
    }



    @Override
    public ResponseEntity<String> archiveProduct(Integer id) {
        Optional<Products> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();
            product.setArchived(true);
            productRepository.save(product);
            return ResponseEntity.ok().body("Product archived successfully");
        } else {
            return ResponseEntity.notFound().build();
        }

    }


    @Override
    public Products AddProductToCategory(Integer productId, Integer categoryId, List<Integer> subCategoryIds) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CategoriesProduct category = categoriesProductRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategoriesProduct(category);

        List<CategoriesProduct> subCategories = categoriesProductRepository.findAllById(subCategoryIds);

        for (CategoriesProduct subCategory : subCategories) {
            CategoriesProduct attachedSubCategory = categoriesProductRepository.findById(subCategory.getIdCategories())
                    .orElseThrow(() -> new RuntimeException("Subcategory not found"));

            product.setSubCategoriesProduct(attachedSubCategory);
        }

        return productRepository.save(product);
    }

    @Override
    public Products getProductByRefProduct(Integer refProduct) {
        return productRepository.findByRefProduct(refProduct)
                .orElseThrow(() -> new RuntimeException("Product not found with reference: " + refProduct));
    }

    @Override
    public Set<Products> getArchivedProducts(Boolean archived) {
        return productRepository.findProductsByArchived(Boolean.TRUE);
    }

    @Override
    public List<Products> getProduitsByIds(List<Integer> idProducts) {
        List<Products> productList = new ArrayList<>();
        for (Integer id : idProducts) {
            Optional<Products> productOptional = productRepository.findById(id);
            if (productOptional.isPresent()) {
                productList.add(productOptional.get());
            }
        }
        return productList;
    }

    @Override
    public void addImagesToProduct(List<MultipartFile> imageFiles, int productId) throws IOException {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produit non trouvé avec l'ID : " + productId));

        List<String> imageUrls = cloudinaryService.uploadImagesToCloudinary(imageFiles, productId);

        for (String imageUrl : imageUrls) {
            Image image = Image.builder()
                    .imageUrl(imageUrl)
                    .produit(product)
                    .build();

            product.getImages().add(image);
        }

        productRepository.save(product);
    }

    @Override
    public List<Image> getImagesForProduct(Integer productId) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produit non trouvé avec l'ID : " + productId));

        return new ArrayList<>(product.getImages());    }


    @Override
    public Set<Products> searchByName(String name) {
        return productRepository.findProductsByProductNameContainingIgnoreCase(name);
    }

    @Override
    public void updateImageForProduct(MultipartFile updatedImageFile, int productId, int imageId) throws IOException {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produit non trouvé avec l'ID : " + productId));

        // Find the image by ID within the product's set of images
        Image existingImage = product.getImages().stream()
                .filter(image -> image.getId() == imageId)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Image non trouvée avec l'ID : " + imageId));

        // Upload the updated image to Cloudinary
        String updatedImageUrl = cloudinaryService.uploadSingleImageToCloudinary(updatedImageFile, productId);

        // Update the existing image's URL
        existingImage.setImageUrl(updatedImageUrl);

        // Save the updated product with the modified image
        productRepository.save(product);
    }

    @Override
    public Products updateProductById(Integer productId, Products updatedProduct) {
        Optional<Products> existingProductOptional = productRepository.findById(productId);

        return existingProductOptional.map(existingProduct -> {
            // Copy non-null properties from updatedProduct to existingProduct
            BeanUtils.copyProperties(updatedProduct, existingProduct, "idProduct", "datePublication", "images", "ratingProductsP");

            // Save the updated product to the database
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
    }
    }









