package org.example.pfa.service;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.pfa.IService.IProductService;
import org.example.pfa.entity.Product;
import org.example.pfa.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class productservice implements IProductService {
    private final ProductRepository repo;

    @Override
    public Product createProduct(Product product) {
        return repo.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }
    @Override
    public Product getProductById(Long id) {
        return repo.findById(id).orElse(null);
    }
    @Override
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = repo.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(productDetails.getName());
            existingProduct.setDescription(productDetails.getDescription());
            existingProduct.setPrice(productDetails.getPrice());
            existingProduct.setStock(productDetails.getStock());
            existingProduct.setCategory(productDetails.getCategory());
            return repo.save(existingProduct);
        }
        return null;
    }
    @Override
    public void deleteProductById(Long id) {
        repo.deleteById(id);
    }
}