package com.grandchefsupreme.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileStorageUtil {

    @Value("${application.storage.profile-photos}")
    private String uploadDir;

    public String saveProfilePhoto(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new IOException("Could not create directory: " + uploadDir);
            }
        }

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File destination = new File(dir, filename);
        file.transferTo(destination);

        return filename;
    }
}