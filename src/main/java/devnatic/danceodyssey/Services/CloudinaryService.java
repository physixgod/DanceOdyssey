package devnatic.danceodyssey.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class CloudinaryService {
    Cloudinary cloudinary;

    public CloudinaryService() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", "dcc4qji59");
        valuesMap.put("api_key", "128238634348998");
        valuesMap.put("api_secret", "b-uWWHQnYkhmXiYJSNGa5_C-iQw");
        cloudinary = new Cloudinary(valuesMap);
    }
    public String uploadSingleImageToCloudinary(MultipartFile file, int productId) throws IOException {
        File convertedFile = convert(file);

        // Generate a unique public_id for the image based on the product ID and a unique identifier.
        String publicId = "product_" + productId + "_" + UUID.randomUUID().toString();

        Map<String, Object> params = new HashMap<>();
        params.put("public_id", publicId);

        Map<String, String> result = cloudinary.uploader().upload(convertedFile, params);

        // Delete the temporary file after uploading.
        Files.delete(convertedFile.toPath());

        // Return the uploaded image URL.
        return result.get("url");
    }
    public List<String> uploadImagesToCloudinary(List<MultipartFile> files, int productId) throws IOException {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            // Convertir MultipartFile en File

            File convertedFile = convert(file);

            // Générer un identifiant public unique pour chaque image basé sur l'ID du produit et un identifiant unique.
            String publicId = "product_" + productId + "_" + UUID.randomUUID().toString();
            // Définir les paramètres pour le téléchargement Cloudinary

            Map<String, Object> params = new HashMap<>();
            params.put("public_id", publicId);
            // Effectuer le téléchargement Cloudinary

            Map<String, String> result = cloudinary.uploader().upload(convertedFile, params);

            // Delete the temporary file after uploading.
            Files.delete(convertedFile.toPath());

            // Add the uploaded image URL to the list.
            imageUrls.add(result.get("url"));
        }

        return imageUrls;
    }

    // Méthode pour convertir MultipartFile en File
    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
