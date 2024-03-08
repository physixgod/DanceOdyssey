package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.ImageData;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Repositories.ProductRepository;
import devnatic.danceodyssey.DAO.Repositories.StorageRepository;
import devnatic.danceodyssey.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class StorageService {
    private  final ProductRepository productRepository;
    private  final StorageRepository storageRepository;
    @Transactional
    public ResponseEntity<String> uploadImage(MultipartFile file, Integer id) {
        try {
            Optional<Products> optionalProduct = productRepository.findById(id);

            if (optionalProduct.isPresent()) {
                Products product = optionalProduct.get();

                ImageData imageData = ImageData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .imageData(ImageUtils.compressImage(file.getBytes()))
                        .produit(product)
                        .build();

                // Save image data
                storageRepository.save(imageData);

                // Set the image data to the product's imageUrl
                product.setImageUrl(imageData.getName());

                // Save the product
                productRepository.save(product);

                return ResponseEntity.ok().body("File uploaded successfully: " + file.getOriginalFilename());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            log.error("Error uploading file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<String> updateImage(MultipartFile file, Integer productId) {
        try {
            Optional<Products> optionalProduct = productRepository.findById(productId);

            if (optionalProduct.isPresent()) {
                Products product = optionalProduct.get();

                // Assuming the product has only one image
                ImageData imageData = product.getImage().stream().findFirst().orElse(null);

                if (imageData != null) {
                    imageData.setName(file.getOriginalFilename());
                    imageData.setType(file.getContentType());
                    imageData.setImageData(ImageUtils.compressImage(file.getBytes()));

                    productRepository.save(product);

                    return ResponseEntity.ok().body("File updated successfully: " + file.getOriginalFilename());
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            log.error("Error updating file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating file: " + e.getMessage());
        }
    }
}
