package com.grandchefsupreme.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FileStorageUtil {

    private final Cloudinary cloudinary;


    public String saveProfilePhoto(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        String original = sanitize(file.getOriginalFilename());
        String publicIdBase = System.currentTimeMillis() + "_" + original;

        Map<?, ?> result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "folder", "grandsmartchef/profile",
                        "public_id", publicIdBase,
                        "overwrite", true,
                        "resource_type", "image"
                )
        );

        Object secureUrl = result.get("secure_url");
        return secureUrl != null ? secureUrl.toString() : null;
    }

    private String sanitize(String original) {
        if (original == null) return "file";
        String name = original.replace("\\", "/");
        name = name.substring(name.lastIndexOf('/') + 1);
        name = name.replaceAll("\\s+", "_");
        name = name.replaceAll("[^a-zA-Z0-9._-]", "");
        return name;
    }
}

//    @Value("${application.storage.profile-photos}")
//    private String uploadDir;
//
//    public String saveProfilePhoto(MultipartFile file) throws IOException {
//        if (file == null || file.isEmpty()) return null;
//
//        File dir = new File(uploadDir);
//        if (!dir.exists()) {
//            boolean created = dir.mkdirs();
//            if (!created) {
//                throw new IOException("Could not create directory: " + uploadDir);
//            }
//        }
//
//        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        File destination = new File(dir, filename);
//        file.transferTo(destination);
//
//        return filename;
//    }
