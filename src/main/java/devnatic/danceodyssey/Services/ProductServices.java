package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.*;
import devnatic.danceodyssey.DAO.Repositories.*;
import devnatic.danceodyssey.Interfaces.IProductServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServices implements IProductServices {
    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CloudinaryService cloudinaryService;
    private final RaitingProductRepository raitingProductRepository;
    private final UserRepository userRepository;
private final ParentCategoryRepository parentCategoryRepository;

    @Override
    public Products addProduct(Products products, Integer parentId) {
        ParentCategory parentCategory = parentCategoryRepository.findById(parentId).orElseThrow(() -> new NotFoundException("SubCategory not found !"));
        products.setParentCategory(parentCategory);
        setProductRef(products);
        uploadImagesToRemote(products.getImages());
        products.setArchived(false);
        updateProductPromotion(products);
        return productRepository.save(products);
    }
    private Float calculatePrixPromotion(Float originalPrice, Integer pourcentagePromotion) {
        Float reduction = originalPrice * (pourcentagePromotion / 100f);
        return originalPrice - reduction;
    }


    private void setProductRef(Products products) {
        if (productRepository.findByRefProduct(products.getRefProduct()).isPresent()) {
            products.setRefProduct(generateNextRefProduct());
        }
    }

    private void updateProductPromotion(Products products) {
        if (checkIsInPromotion(products.getPourcentagePromotion())) {
            products.setIsPromotion(true);
            products.setPricePromotion(calculatePrixPromotion(products.getPrice(), products.getPourcentagePromotion()));
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

    private void uploadImagesToRemote(Set<Image> images) {
        images.forEach(
                image -> {
                    String imageUrl = cloudinaryService.uploadSingleImageToCloudinary(image.getImage());
                    image.setImageUrl(imageUrl);
                }
        );
    }

    @Override
    public Products updateProductById(Integer productId, Products updatedProduct) {

        uploadImagesToRemote(updatedProduct.getImages());

        Optional<Products> existingProductOptional = productRepository.findById(productId);
        return existingProductOptional.map(existingProduct -> {
            BeanUtils.copyProperties(updatedProduct, existingProduct, "idProduct", "datePublication", "ratingProductsP", "categoriesProduct", "subCategoriesProduct");

            if (existingProduct.getQuantity() == 0) {
                existingProduct.setProductState(true);
            } else {
                existingProduct.setProductState(false);
            }
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
    public Products getProductById(Integer idProduct) {
        return productRepository.findById(idProduct).orElseThrow(() -> new NotFoundException("Product not found !"));
    }

    @Override
    public List<Image> getImagesForProduct(Integer productId) {
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
        Products product = productRepository.findById(id).orElseThrow(()-> new NotFoundException("Product not found"));
        product.setArchived(true);
        productRepository.save(product);


    }
    @Override
    public void unarchiveProduct(Integer id) {
        Products product = productRepository.findById(id).orElseThrow(()-> new NotFoundException("Product not found"));
            product.setArchived(false);
            productRepository.save(product);

    }


    @Override
    public void addRatingToProduct(Integer ratingId, Integer productId, Integer userId) {
        Products product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("product Not found"));
        RatingProducts rating = raitingProductRepository.findById(ratingId).orElseThrow(() -> new NotFoundException(" raiting Not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(" user Not found"));
        user.getRatingProductsS().add(rating);
        product.getRatingProductsP().add(rating);
        productRepository.save(product);
        raitingProductRepository.save(rating);
    }


    @Override
    public List<Products> getLast5Products() {
        return productRepository.findTop5ByOrderByDatePublicationDesc();
    }


    @Override
    public List<Products> getTopRatingProducts() {
        return productRepository.findTop5ByOrderBySumOfScoresDesc();
    }


    @Override
    public List<Products> getPromotionalProducts() {
        return productRepository.findByIsPromotion(true);
    }



}










