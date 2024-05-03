package devnatic.danceodyssey.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
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
    public void deleteImageFromCloudinary(String imageUrl) throws IOException {
        // Récupérer le public ID de l'image à partir de son URL
        String publicId = getPublicIdFromImageUrl(imageUrl);

        // Supprimer l'image de Cloudinary en utilisant le public ID
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    private String getPublicIdFromImageUrl(String imageUrl) {
        // Extraire le public ID de l'URL de l'image
        String[] parts = imageUrl.split("/");
        String lastPart = parts[parts.length - 1];
        String publicId = lastPart.substring(0, lastPart.lastIndexOf("."));
        return publicId;
    }
    public List<String> updateMediaFilesOnCloudinary(List<MultipartFile> newMediaFiles, int productId, List<String> existingMediaUrls) throws IOException {
        List<String> updatedMediaUrls = new ArrayList<>();

        // Supprimer les anciens fichiers média de Cloudinary
        for (String oldMediaUrl : existingMediaUrls) {
            String publicId = getPublicIdFromImageUrl(oldMediaUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }

        // Parcourir chaque nouveau fichier média
        for (MultipartFile file : newMediaFiles) {
            File convertedFile = convert(file);
            String publicId = "product_" + productId + "_" + UUID.randomUUID().toString();
            Map<String, Object> params = new HashMap<>();
            params.put("public_id", publicId);

            // Vérifie le type de fichier (image ou vidéo)
            String resourceType = file.getContentType().startsWith("image") ? "image" : "video";
            if (resourceType.equals("video")) {
                params.put("resource_type", "video");
            }

            // Télécharge le nouveau fichier sur Cloudinary
            Map<String, String> result = cloudinary.uploader().upload(convertedFile, params);

            // Supprime le fichier converti
            Files.delete(convertedFile.toPath());

            // Ajoute l'URL du fichier mis à jour à la liste des URLs
            updatedMediaUrls.add(result.get("url"));
        }

        return updatedMediaUrls;
    }

    public String uploadSingleImageToCloudinary(MultipartFile file, int productId) throws IOException {
        File convertedFile = convert(file);

        String publicId = "product_" + productId + "_" + UUID.randomUUID().toString();

        Map<String, Object> params = new HashMap<>();
        params.put("public_id", publicId);

        Map<String, String> result = cloudinary.uploader().upload(convertedFile, params);

        Files.delete(convertedFile.toPath());

        return result.get("url");
    }

    // Méthode pour télécharger de la musique sur Cloudinary
    public String uploadSingleMusicToCloudinary(MultipartFile file, int productId) throws IOException {
        // Utilisez le nom de fichier original ou un nom unique pour le fichier téléchargé
        String filename = "product_" + productId + "_" + file.getOriginalFilename();
        // Téléchargez le fichier sur Cloudinary
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("public_id", filename));
        // Récupérez l'URL de téléchargement de la musique à partir du résultat
        return (String) result.get("url");
    }
    public List<String> uploadMediaFilesToCloudinary(List<MultipartFile> mediaFiles, int productId) throws IOException {
        List<String> mediaUrls = new ArrayList<>();

        for (MultipartFile file : mediaFiles) {
            File convertedFile = convert(file);
            String publicId = "product_" + productId + "_" + UUID.randomUUID().toString();
            Map<String, Object> params = new HashMap<>();
            params.put("public_id", publicId);

            // Vérifie le type de fichier (image ou vidéo)
            String resourceType = file.getContentType().startsWith("image") ? "image" : "video";
            if (resourceType.equals("video")) {
                params.put("resource_type", "video");
            }
            // Redimensionne l'image si c'est une image
            if (resourceType.equals("image")) {
                params.put("transformation", new Transformation<>().width(259).height(259).crop("fill"));
            }

            // Télécharge le fichier sur Cloudinary
            Map<String, String> result = cloudinary.uploader().upload(convertedFile, params);

            // Supprime le fichier converti
            Files.delete(convertedFile.toPath());

            // Ajoute l'URL du fichier téléchargé à la liste des URLs
            mediaUrls.add(result.get("url"));
        }

        return mediaUrls;
    }

    public List<String> updateMediaFilesOnCloudinary(List<MultipartFile> newMediaFiles, int productId) throws IOException {
        List<String> updatedMediaUrls = new ArrayList<>();

        for (MultipartFile file : newMediaFiles) {
            File convertedFile = convert(file);
            String publicId = "product_" + productId + "_" + UUID.randomUUID().toString();
            Map<String, Object> params = new HashMap<>();
            params.put("public_id", publicId);

            // Check file type (image or video)
            String resourceType = file.getContentType().startsWith("image") ? "image" : "video";
            if (resourceType.equals("video")) {
                params.put("resource_type", "video");
            }

            // Upload the new file to Cloudinary
            Map<String, String> result = cloudinary.uploader().upload(convertedFile, params);

            // Delete the converted file
            Files.delete(convertedFile.toPath());

            // Add the uploaded file's URL to the list
            updatedMediaUrls.add(result.get("url"));
        }

        return updatedMediaUrls;
    }



    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}