package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(4);

    public ProductService() {
        products.add(new Product(1L, "Lenovo Legion Pro", 215.80));
        products.add(new Product(2L, "Premium Keyboard", 158.00));
        products.add(new Product(3L, "RTX 5090", 399.99));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    // FIX: Returns Optional<Product> to satisfy the ProductController (REST)
    public Optional<Product> getProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public Product createProduct(Product product) {
        product.setId(nextId.getAndIncrement());
        products.add(product);
        return product;
    }

    public Product createProduct(String name, Double price) {
        Product newProduct = new Product(nextId.getAndIncrement(), name, price);
        products.add(newProduct);
        return newProduct;
    }

    // FIX: Returns Optional<Product> to satisfy the ProductController (REST)
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            return existingProduct;
        }
        return Optional.empty();
    }

    public boolean deleteProduct(Long id) {
        return products.removeIf(p -> p.getId().equals(id));
    }
}