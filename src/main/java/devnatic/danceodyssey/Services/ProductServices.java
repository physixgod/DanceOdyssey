package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.*;
import devnatic.danceodyssey.DAO.Repositories.*;
import devnatic.danceodyssey.Interfaces.IProductServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServices implements IProductServices {
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final RaitingProductRepository raitingProductRepository;
    private final UserRepo userRepository;
    private final  SubCategoryRepository subCategoryRepository;
private final ParentCategoryRepository parentCategoryRepository;

    @Override
    public Products addProduct(Products products, Integer parentId ,Integer subCategoryId ){
        ParentCategory parentCategory = parentCategoryRepository.findById(parentId).orElseThrow(() -> new NotFoundException("parentcateory not found !"));
        products.setParentCategory(parentCategory);
        // Récupérer la sous-catégorie en fonction de l'ID spécifié dans le JSON

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new NotFoundException("Sub category not found!"));

        products.setParentCategory(parentCategory);
        products.setSubCategories(subCategory);

        // Assurez-vous que le produit est associé à la catégorie parente correcte
        updateProductState(products);
        products.setArchived(false);
        updateProductPromotion(products);
        return productRepository.save(products);
    }


    private void updatePromotionEndDate(Products product) {
        if (product.getPromotionEndDate() == null) {
            // Calculer la date de fin de promotion en ajoutant la durée de la promotion à la date de publication
            LocalDate promotionEndDate = product.getDatePublication().plusDays(product.getPourcentagePromotion());

            product.setPromotionEndDate(promotionEndDate);
        }
    }
    private Float calculatePrixPromotion(Float originalPrice, Integer pourcentagePromotion) {
        Float reduction = originalPrice * (pourcentagePromotion / 100f);
        return originalPrice - reduction;
    }
    // Méthode pour mettre à jour l'état du produit en fonction de sa quantité
    private void updateProductState(Products product) {
        if (product.getQuantity() == 0) {
            product.setProductState(true);
        } else {
            product.setProductState(false);
        }
    }


    private void updateProductPromotion(Products products) {
        if (checkIsInPromotion(products.getPourcentagePromotion())) {
            updatePromotionEndDate(products);
            products.setIsPromotion(true);
            products.setPricePromotion(calculatePrixPromotion(products.getPrice(), products.getPourcentagePromotion()));
        } else {
            // Si le produit n'est pas en promotion, assurez-vous que la date de fin de promotion est nulle
            products.setPromotionEndDate(null);
        }
    }


    private boolean checkIsInPromotion(Integer price) {
        return price > 0;
    }

    private Integer generateNextRefProduct() {
        Integer lastProductId = productRepository.findMaxProductId();

        if (lastProductId != null) {
            return lastProductId + 1;
        } else {
            return 1;
        }
    }



    @Override
    public Products updateProductById(Integer productId, Products updatedProduct) {
        Optional<Products> existingProductOptional = productRepository.findById(productId);

        return existingProductOptional.map(existingProduct -> {
            BeanUtils.copyProperties(updatedProduct, existingProduct, "idProduct", "parentCategory", "subCategory", "datePublication", "images", "ratingProductsP");

            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
    }


    @Override
    public List<Products> ShowAllProducts() {
        return productRepository.findAll();
    }


    @Override
    public Set<Products> getArchivedProducts(Boolean archived) {
        return productRepository.findProductsByArchived(Boolean.TRUE);
    }

    @Override
    public List<Products> getProduitsByIds(List<Integer> idProducts){
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
    public List<MediaFiles> getImagesForProduct(Integer productId) {
        Optional<Products> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Products product = productOptional.get();
            return new ArrayList<>(product.getImages());
        }
        return new ArrayList<>();
    }

    @Override
    public Set<Products> searchByName(String name) {
        return productRepository.findProductsByProductNameContainingIgnoreCase(name);
    }

    @Override
    public void archiveProduct(Integer id) {
        Optional<Products> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();
            product.setArchived(true);
            productRepository.save(product);


        }
    }

    @Override
    public void unarchiveProduct(Integer id) {
        Products product = productRepository.findById(id).orElseThrow(()-> new NotFoundException("Product not found"));
            product.setArchived(false);
            productRepository.save(product);

    }


    @Override
    public void addRatingToProduct(Integer ratingId, Integer productId, Long userId) {
        // Retrieve the product, rating, and user from their respective repositories
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        RatingProduct rating = raitingProductRepository.findById(ratingId)
                .get();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Add the rating to the user's set of ratings
        user.getRatingProductsS().add(rating);


        // Add the rating to the product's set of ratings
        product.getRatingProductsP().add(rating);

        // Save the updated product and rating
        productRepository.save(product);
        raitingProductRepository.save(rating);
        // Recalculate the average score for the product
        Set<RatingProduct> ratings = product.getRatingProductsP();

        // Calculate the total score
        int totalScore = 0;
        for (RatingProduct r : ratings) {
            totalScore += r.getScore();
        }

        // Calculate the new average score
        float averageScore = (float) totalScore / ratings.size();

        // Update the average score of the product
        product.setAvreageScore(averageScore);

        // Save the updated product
        productRepository.save(product);
    }



    @Override
    public List<Products> getLast5Products() {
        return productRepository.findTop5ByOrderByDatePublicationDesc();
    }

    @Override
    public List<Products> getTop10OrderProduct() {
        return productRepository.findTop10ByOrderByQuantiteVendueDesc();
    }


    @Override
    public List<Products> getTopRatingProducts() {
        return productRepository.findTop5ByOrderBySumOfScoresDesc();
    }


    @Override
    public List<Products> getPromotionalProducts() {
        return productRepository.findByIsPromotion(true);
    }


    @Override
    public void addMediaToProduct(List<MultipartFile> mediaFiles, int productId) throws IOException{

        Optional<Products> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Products product = productOptional.get();
            List<String> mediaUrls = cloudinaryService.uploadMediaFilesToCloudinary(mediaFiles, productId);
            for (String mediaUrl : mediaUrls) {
                MediaFiles image = MediaFiles.builder()
                        .imageUrl(mediaUrl)
                        .product(product)
                        .build();

                product.getImages().add(image);
            }
            productRepository.save(product);
        }
    }



    @Override
    public List<Products> getProductsByParentCategory(ParentCategory parentCategory) {
        return productRepository.findByParentCategory(parentCategory);
    }

    @Override
    public List<Products> getProductsByParentCategoryAndProductName(Integer parentCategoryId, String productName) {
        ParentCategory parentCategory = parentCategoryRepository.findById(parentCategoryId)
                .orElseThrow(() -> new NotFoundException("Parent category not found!"));

        Set<Products> products = productRepository.findByParentCategoryAndProductNameContainingIgnoreCase(parentCategory, productName);

        return new ArrayList<>(products);
    }

    @Override
    public Set<RatingProduct> getProductReactions(Integer productId) {
        Optional<Products> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            return null; // Or handle accordingly
        }
        Products product = productOptional.get();
        return product.getRatingProductsP();
    }

    @Override
    public List<Products> getProductsBySubCategoryId(Integer subCategoryId) {
        return productRepository.findProductsBySubCategories_Id(subCategoryId);
    }

    @Override
    public List<Products> findTop5ProductsByParentCategoryId(Integer parentCategoryId) {
        return productRepository.findTop5ByParentCategory_IdOrderByQuantiteVendueDesc(parentCategoryId);
    }

    @Override
    public List<Products> findTop5ProductsIspromotion(Integer parentCategoryId) {
        return productRepository.findPromotionalProductsByParentCategoryId(parentCategoryId);
    }

    @Override
    public List<Products> getLast5ProductsByParentCategoryId(Integer parentCategoryId) {
        // Récupérer la catégorie parente par son ID
        ParentCategory parentCategory = parentCategoryRepository.findById(parentCategoryId)
                .orElseThrow(() -> new NotFoundException("Parent category not found!"));

        // Récupérer les 5 derniers produits de cette catégorie parente
        return productRepository.findTop5ByParentCategoryOrderByDatePublicationDesc(parentCategory);
    }

    @Override
    public List<Products> getAvreagescoreProductsByParentCategoryId(Integer parentCategoryId) {

        return productRepository.findTop5ByParentCategoryIdOrderByAverageScoreDesc(parentCategoryId);
    }

    @Override
    public List<String> updateMediaForProduct(List<MultipartFile> newMediaFiles, int productId, int mediaId) throws IOException {
        Optional<Products> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new NoSuchElementException("Product with ID " + productId + " not found");
        }

        Products product = productOptional.get();

        // 1. Handle existing media based on mediaId (assuming mediaId refers to a specific media to update)
        Optional<MediaFiles> mediaToUpdateOptional = product.getImages().stream()
                .filter(media -> media.getId() == mediaId) // assuming MediaFiles has an 'id' field
                .findFirst();

        if (mediaToUpdateOptional.isPresent()) {
            MediaFiles mediaToUpdate = mediaToUpdateOptional.get();

            // Delete existing media from Cloudinary if updating an existing media file
            if (!mediaToUpdate.getImageUrl().isEmpty()) {
                cloudinaryService.deleteImageFromCloudinary(mediaToUpdate.getImageUrl());
            }

            // Update the image URL
            List<String> updatedMediaUrls = cloudinaryService.updateMediaFilesOnCloudinary(newMediaFiles, productId);
            if (!updatedMediaUrls.isEmpty()) {
                mediaToUpdate.setImageUrl(updatedMediaUrls.get(0)); // Assuming only one URL is returned
            }

            // Save the updated product (optional)
            productRepository.save(product);

            // Return the updated media URL
            return updatedMediaUrls;
        } else {
            throw new NoSuchElementException("Media with ID " + mediaId + " not found for product with ID " + productId);
        }
    }

    @Override
    public List<Products> getProductsByParentCategoryId(Integer subCategoryId) {
            SubCategory subCategory = getSubCategoryById(subCategoryId);
            return subCategory.getProducts();
        }

        private SubCategory getSubCategoryById(Integer subCategoryId) {
            return subCategoryRepository.findById(subCategoryId)
                    .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        }    }











