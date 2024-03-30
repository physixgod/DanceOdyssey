package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.*;
import devnatic.danceodyssey.DAO.Repositories.CategoriesProductRepository;
import devnatic.danceodyssey.DAO.Repositories.ProductRepository;
import devnatic.danceodyssey.DAO.Repositories.RaitingProductRepository;
import devnatic.danceodyssey.DAO.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class Product_Services implements IProduct_Services {
    private final ProductRepository productRepository;
    private final CategoriesProductRepository categoriesProductRepository;
    private final CloudinaryService cloudinaryService;
    private  final RaitingProductRepository raitingProductRepository;
    private  final UserRepository userRepository;




    @Override
    public Products AddProduct(Products products) {
        products.setRefProduct(generateNextRefProduct());

        if (productRepository.existsByRefProduct(products.getRefProduct())) {
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
    public void archiveProduct(Integer id) {
        Optional<Products> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();
            product.setArchived(true);
            productRepository.save(product);


        }
    }
    @Override
    public void AddProductToCategory(Integer productId, Integer categoryId, List<Integer> subCategoryIds) {
        productRepository.findById(productId).ifPresent(product -> {
            Optional<CategoriesProduct> categoryOptional = categoriesProductRepository.findById(categoryId);
            if (categoryOptional.isPresent()) {
                CategoriesProduct category = categoryOptional.get();
                product.setCategoriesProduct(category);

                List<CategoriesProduct> subCategories = categoriesProductRepository.findAllById(subCategoryIds);
                for (CategoriesProduct subCategory : subCategories) {
                    Optional<CategoriesProduct> subCategoryOptional = categoriesProductRepository.findById(subCategory.getIdCategories());
                    if (subCategoryOptional.isPresent()) {
                        CategoriesProduct attachedSubCategory = subCategoryOptional.get();
                        product.setSubCategoriesProduct(attachedSubCategory);
                    }
                }
                productRepository.save(product);
            }
        });
    }
    @Override
    public void getProductByRefProduct(Integer refProduct) {
         productRepository.findByRefProduct(refProduct);
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
        Optional<Products> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Products product = productOptional.get();
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
    public Products updateProductById(Integer productId, Products updatedProduct) {
        Optional<Products> existingProductOptional = productRepository.findById(productId);

        return existingProductOptional.map(existingProduct -> {
            // Copier les propriétés de l'objet mis à jour
            BeanUtils.copyProperties(updatedProduct, existingProduct, "idProduct", "datePublication", "images", "ratingProductsP", "categoriesProduct", "subCategoriesProduct");

            // Met à jour le productState en fonction de la quantité de produits
            if (existingProduct.getQuantity() == 0) {
                existingProduct.setProductState(true);
            } else {
                existingProduct.setProductState(false);
            }
            // Mettre à jour le produit dans la base de données
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
    }

    @Override
    public void  unarchiveProduct(Integer id) {
        Optional<Products> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(product -> {
            product.setArchived(false);
            productRepository.save(product);
        });
    }

    @Override
    public Set<Products> getProductsByParentCategory(Integer parentId) {
        return productRepository.findProductsByCategoriesProduct_IdCategories(parentId);
    }

    @Override
    public Set<Products> getProductsSubCategory(Integer subCategoryId) {
        return productRepository.findProductsBySubCategoriesProduct_IdCategories(subCategoryId);
    }

    @Override
    public List<Products> searchProductsByNameAndCategory(String name, Integer categoryId) {
        return productRepository.findByProductNameContainingIgnoreCaseAndCategoriesProduct_IdCategories(name, categoryId);
    }

    @Override
    public List<Products> searchProductsByNameAndSubCategory(String name, Integer subCategoryId) {
        return productRepository.findByProductNameContainingIgnoreCaseAndSubCategoriesProduct_IdCategories(name, subCategoryId);
    }



    @Override
    public void AddRatingToProduct(Integer ratingId, Integer productId, Integer userId) {
        Products product = productRepository.findById(productId).orElse(null);
        RatingProducts rating = raitingProductRepository.findById(ratingId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (product != null && rating != null && user != null) {
            user.getRatingProductsS().add(rating);

            product.getRatingProductsP().add(rating);
            productRepository.save(product);
            raitingProductRepository.save(rating);
        }
    }

    @Override
    public List<Products> getLast5Products() {
        return productRepository.findTop5ByOrderByDatePublicationDesc();
    }



    @Override
    public List<Products> getTopRatingProducts() {
        return productRepository.findTop5ByOrderByRatingProductsPScoreDesc();
    }

    @Override
    public Float calculateReducedPrice(Integer productId, Integer pourcentagePromotion) {
        Products product = productRepository.findById(productId).orElse(null);
        if (product != null && pourcentagePromotion != null && pourcentagePromotion > 0) {
            Float originalPrice = product.getPrice();
            Float prixPromotion = calculatePrixPromotion(originalPrice, pourcentagePromotion);
            product.setPrixPromotion(prixPromotion);
            product.setIsPromotion(true); // Définir isPromotion comme true

            product.setPourcentagePromotion(pourcentagePromotion);
            productRepository.save(product);
            return prixPromotion;
        } else {
            // Gérer le cas où le produit ou le pourcentage de promotion est invalide
            return null;
        }
    }

    @Override
    public List<Products> getPromotionalProducts() {
        return productRepository.findByIsPromotion(true);
    }




    private Float calculatePrixPromotion(Float originalPrice, Integer pourcentagePromotion) {
        // Calculer le prix promotionnel en fonction du pourcentage de promotion
        Float reduction = originalPrice * (pourcentagePromotion / 100f);
        return originalPrice - reduction;
    }



    @Override
    public String SumRatting(Integer id, Integer id2) {

        int a=0;
        int b=0 ;
        Products p=  productRepository.findById(id).orElse(null);
        Products p2= productRepository.findById(id2).orElse(null) ;

        for (RatingProducts j: p2.getRatingProductsP()) {
            b += j.getScore();
        }

        for(RatingProducts i:p.getRatingProductsP()){
            a+=i.getScore();
        }
        System.out.println("♦♦♦♦♦♦♦♦♦♦"+b);

        if ( a>b )
            return ("Produit 1 Better than B " )
                    ;
        else
            return (" Produit 2 Better than Produit 1 ") ;

    }



}










