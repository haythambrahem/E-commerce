package org.example.pfa.IService;

import org.example.pfa.entity.Product;

import java.util.List;

public interface IProductService {
    Product createProduct (Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct( Long id, Product product);
    void deleteProductById(Long id);












}
