
package org.example.pfa.TestService;

import org.example.pfa.entity.Product;
import org.example.pfa.repository.ProductRepository;
import org.example.pfa.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductWithoutImage() throws IOException {
        Product product = new Product();
        product.setName("tulipe");
        product.setPrice(BigDecimal.valueOf(26));

        when(productRepo.save(any(Product.class))).thenReturn(product);

        Product result = productService.createProduct(product, null);

        assertNotNull(result);
        assertEquals("tulipe", result.getName());
        verify(productRepo, times(1)).save(any(Product.class));
    }

    @Test
    void testCreateProductWithImage() throws IOException {
        Product product = new Product();
        product.setName("tulipe");

        // Mock du fichier image
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getOriginalFilename()).thenReturn("image.jpg");

        when(productRepo.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        Product result = productService.createProduct(product, mockFile);

        assertNotNull(result.getImageUrl());
        assertTrue(result.getImageUrl().contains("/uploads/"));
        verify(productRepo).save(any(Product.class));
    }

    @Test
    void testGetAllProducts() {
        List<Product> mockList = Arrays.asList(new Product(), new Product());
        when(productRepo.findAll()).thenReturn(mockList);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepo, times(1)).findAll();
    }

    @Test
    void testGetProductByIdFound() {
        Product product = new Product();
        product.setId(1L);
        product.setName("tulipe");
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertEquals("tulipe", result.getName());
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productService.getProductById(99L));
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepo).deleteById(1L);
        productService.deleteProduct(1L);
        verify(productRepo, times(1)).deleteById(1L);
    }

}
