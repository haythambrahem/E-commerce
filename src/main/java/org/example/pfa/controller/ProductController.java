package org.example.pfa.controller;

import org.example.pfa.IService.IProductService;
import org.example.pfa.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
      private IProductService productservice;


    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productservice.createProduct(product);
    }
    @GetMapping
    public List<Product> getAllProducts() {
        return productservice.getAllProducts();
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productservice.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productservice.updateProduct(id, product);
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productservice.deleteProductById(id);
    }

}

