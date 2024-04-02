package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.*;
import devnatic.danceodyssey.DAO.Repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServices implements IProductServices {
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final RaitingProductRepository raitingProductRepository;
    private final UserRepository userRepository;
private final ParentCategoryRepository parentCategoryRepository;

    @Override
    public Products addProduct(Products products, Integer parentId) {
        ParentCategory parentCategory = parentCategoryRepository.findById(parentId).orElseThrow(() -> new NotFoundException("SubCategory not found !"));
        products.setParentCategory(parentCategory);
        setProductRef(products);
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



    @Override
    public Products updateProductById(Integer productId, Products updatedProduct) {
        Optional<Products> existingProductOptional = productRepository.findById(productId);

        return existingProductOptional.map(existingProduct -> {
            BeanUtils.copyProperties(updatedProduct, existingProduct, "idProduct", "datePublication", "images", "ratingProductsP");

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


    @Override
    public void addImagesToProduct(List<MultipartFile> imageFiles, int productId) throws IOException {

        Optional<Products> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Products product = productOptional.get();
            List<String> imageUrls = cloudinaryService.uploadImagesToCloudinary(imageFiles, productId);
            for (String imageUrl : imageUrls) {
                Image image = Image.builder()
                        .imageUrl(imageUrl)
                        .product(product)
                        .build();

                product.getImages().add(image);
            }
            productRepository.save(product);
        }
    }

    @Override
    public void updateImageForProduct(MultipartFile updatedImageFile, int productId, int imageId) throws IOException {

        Optional<Products> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Products product = productOptional.get();

            Optional<Image> existingImageOptional = product.getImages().stream()
                    .filter(image -> image.getId() == imageId)
                    .findFirst();

            if (existingImageOptional.isPresent()) {
                Image existingImage = existingImageOptional.get();

                String updatedImageUrl = cloudinaryService.uploadSingleImageToCloudinary(updatedImageFile, productId);

                existingImage.setImageUrl(updatedImageUrl);

                productRepository.save(product);
            } }
    }

    @Override
    public List<Products> getProductsByParentCategory(ParentCategory parentCategory) {
        return productRepository.findByParentCategory(parentCategory);
    }

    @Override
    public List<Products> getProductsByParentCategoryId(Integer parentId) {
        return productRepository.findByParentCategory_Id(parentId);
    }
}










