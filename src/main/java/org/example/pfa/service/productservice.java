package org.example.pfa.service;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.pfa.entity.Product;
import org.example.pfa.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class productservice {
    private ProductRepository repo;


    public List<Product> listAll() {
        return repo.findAll();
    }



    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Product save(Product product) {
        return product;
    }

    public Product findById(Long id) {
        return null;
    }
}
