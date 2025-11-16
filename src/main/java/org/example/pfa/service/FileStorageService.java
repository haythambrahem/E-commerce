package org.example.pfa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    // Lire le dossier depuis application.properties
    @Value("${upload.dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {

        // Créer le dossier s'il n'existe pas
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) uploadFolder.mkdirs();

        // Générer un nom unique
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Chemin complet
        Path destination = Paths.get(uploadFolder.getAbsolutePath(), fileName);

        // Sauvegarder le fichier
        file.transferTo(destination.toFile());

        // URL pour Angular (peut être adaptée selon ton mapping static)
        return "/uploads/" + fileName;
    }
}
