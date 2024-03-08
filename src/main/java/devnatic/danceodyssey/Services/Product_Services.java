package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.CategoriesProduct;
import devnatic.danceodyssey.DAO.Entities.Competition;
import devnatic.danceodyssey.DAO.Entities.ImageData;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Repositories.CategoriesProductRepository;
import devnatic.danceodyssey.DAO.Repositories.ImageRepository;
import devnatic.danceodyssey.DAO.Repositories.ProductRepository;
import devnatic.danceodyssey.DAO.Repositories.StorageRepository;
import devnatic.danceodyssey.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class Product_Services implements IProduct_Services {
    private final ProductRepository productRepository;
    private final CategoriesProductRepository categoriesProductRepository;
    private  final ImageRepository imageRepository;

    @Override
    public Products AddProduct(Products products) {
        // Générer automatiquement une référence de produit auto-incrémentée
        products.setRefProduct(generateNextRefProduct());

        // Vérifier si la référence du produit est déjà utilisée
        if (productRepository.existsByRefProduct(products.getRefProduct())) {
            throw new RuntimeException("Product reference already exists.");
        }

        // Force la valeur de archived à false lors de l'insertion
        products.setArchived(false);

        // Enregistrer le produit dans la base de données
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
        // Récupérer le dernier ID de produit depuis la base de données
        Integer lastProductId = productRepository.findMaxProductId();

        // Incrémenter le dernier ID ou utilisez une logique appropriée pour la première référence
        if (lastProductId != null) {
            return lastProductId + 1;
        } else {
            // Logique pour la première référence si la table est vide
            // Vous pouvez également choisir une autre approche si nécessaire
            return 1000;
        }
    }

    @Override
    public List<ImageData> getImagesForProduct(Integer idProduct) {
        return imageRepository.findByProduit_IdProduct(idProduct);

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
        // Récupérer le produit depuis la base de données
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Récupérer la catégorie principale depuis la base de données
        CategoriesProduct category = categoriesProductRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Associer le produit à la catégorie principale
        product.setCategoriesProduct(category);

        // Associer le produit à chaque sous-catégorie
        List<CategoriesProduct> subCategories = categoriesProductRepository.findAllById(subCategoryIds);

        // Mettre à jour la liste des produits dans chaque sous-catégorie
        for (CategoriesProduct subCategory : subCategories) {
            // Fetch the current state from the database and reattach
            CategoriesProduct attachedSubCategory = categoriesProductRepository.findById(subCategory.getIdCategories())
                    .orElseThrow(() -> new RuntimeException("Subcategory not found"));

            // Associer le produit à la sous-catégorie
            product.setSubCategoriesProduct(attachedSubCategory);
        }

        // Enregistrez les modifications dans la base de données
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
        List<Products> productList = new ArrayList<>(); // Change variable name to productList
        for (Integer id : idProducts) {
            Optional<Products> productOptional = productRepository.findById(id); // Change variable name to productOptional
            if (productOptional.isPresent()) {
                productList.add(productOptional.get());
            }
        }
        return productList;
    }    }



