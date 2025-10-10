package org.example.pfa.controller;


import org.example.pfa.entity.Product;
import org.example.pfa.service.productservice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private productservice service;
    public void ProductController(productservice service) { this.service = service; }

    @GetMapping
    public List<Product> list() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        Product p = service.findById(id);
        return p == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(p);
    }

    @PostMapping
    public ResponseEntity<Product> create
            (@RequestBody Product product) {
        Product saved = service.save(product);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

