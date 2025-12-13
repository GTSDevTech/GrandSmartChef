package com.grandchefsupreme.controller;

import jakarta.validation.Valid;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/uploads/profile")
public class UploadController {

    private final Path root = Paths.get("/data/uploads/profile");

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{filename:.+}")
    public ResponseEntity<UrlResource> getFile(@Valid @PathVariable String filename) {
        try {
            Path file = root.resolve(filename).normalize();
            UrlResource resource = new UrlResource(file.toUri());

            if(!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaTypeFactory.getMediaType(resource)
                    .orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}