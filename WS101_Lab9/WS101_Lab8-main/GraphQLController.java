package com.example.demo.graphqlcontroller;

import com.example.demo.Product;
import com.example.demo.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class GraphQLController {

    private final ProductService productService;

    public GraphQLController(ProductService productService) {
        this.productService = productService;
    }

    @QueryMapping
    public List<Product> allProducts() {
        return productService.getAllProducts();
    }

    // FIX: Unwrap Optional to return Product or null
    @QueryMapping
    public Product productById(@Argument Long id) {
        return productService.getProductById(id).orElse(null);
    }

    @MutationMapping
    public Product addProduct(
            @Argument String name,
            @Argument Double price
    ) {
        return productService.createProduct(name, price);
    }

    // FIX: Unwrap Optional to return Product or null
    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct).orElse(null);
    }

    @MutationMapping
    public String deleteProduct(@Argument Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? "Product with ID " + id + " deleted successfully." : "Product with ID " + id + " not found or deletion failed.";
    }
}