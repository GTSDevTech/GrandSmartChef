package com.grandchefsupreme.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileStorageUtil {

    private final Cloudinary cloudinary;

    public String saveProfilePhoto(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        try {
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
            if (secureUrl == null) {
                log.error("Cloudinary upload OK but secure_url missing. Result keys: {}", result.keySet());
                throw new IOException("Cloudinary upload failed: secure_url missing");
            }

            return secureUrl.toString();

        } catch (Exception ex) {
            // Esto te va a decir la causa REAL en Render logs
            log.error("Cloudinary upload error: {}", ex.getMessage(), ex);
            if (ex instanceof IOException io) throw io;
            throw new IOException("Cloudinary upload error: " + ex.getMessage(), ex);
        }
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
