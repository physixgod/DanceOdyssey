package devnatic.danceodyssey.Services;

import com.cloudinary.Cloudinary;
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

    public String uploadSingleImageToCloudinary(MultipartFile file) {
        File convertedFile = null;
        try {
            if(file == null){
                return null;
            }
            convertedFile = convert(file);


            String publicId = "product_" + UUID.randomUUID();

            Map<String, Object> params = new HashMap<>();
            params.put("public_id", publicId);

            Map<String, String> result = cloudinary.uploader().upload(convertedFile, params);

            Files.delete(convertedFile.toPath());

            return result.get("url");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
