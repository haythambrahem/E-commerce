package org.example.pfa.service;

import org.example.pfa.entity.Product;
import org.example.pfa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    private static final String UPLOAD_DIR = "uploads/";

    public Product createProduct(Product product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            File destination = new File(UPLOAD_DIR + fileName);
            imageFile.transferTo(destination);

            product.setImageUrl("/uploads/" + fileName);
        }

        return productRepo.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Produit introuvable"));
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}
