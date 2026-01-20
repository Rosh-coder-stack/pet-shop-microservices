package com.petshop.product.controller;

import com.petshop.product.exception.ProductNotFoundException;
import com.petshop.product.model.Product;
import com.petshop.product.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.List;

@RestController //This class handles HTTP requests (API)
@RequestMapping("/api/products") //base URL.

public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/filter")
    public List<Product> filterProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max) {

        if (category != null && min != null && max != null) {
            return productRepository
                    .findByCategoryIgnoreCaseAndPriceBetween(category, min, max);
        }

        if (category != null) {
            return productRepository.findByCategoryIgnoreCase(category);
        }

        if (min != null && max != null) {
            return productRepository.findByPriceBetween(min, max);
        }
        return productRepository.findAll();
    }

    @GetMapping("/sort")
    public List<Product> sortProducts(@RequestParam String by) {

        if (by.equalsIgnoreCase("priceAsc")) {
            return productRepository.findAll(
                    org.springframework.data.domain.Sort.by("price").ascending()
            );
        }

        if (by.equalsIgnoreCase("priceDesc")) {
            return productRepository.findAll(
                    org.springframework.data.domain.Sort.by("price").descending()
            );
        }

        return productRepository.findAll();
    }


    @GetMapping("/page")
    public org.springframework.data.domain.Page<Product> getProductsWithPagination(
            @RequestParam int page,
            @RequestParam int size) {

        org.springframework.data.domain.Pageable pageable =
                org.springframework.data.domain.PageRequest.of(page, size);

        return productRepository.findAll(pageable);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "Product deleted";
    }
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product newProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(newProduct.getName());
        product.setCategory(newProduct.getCategory());
        product.setPrice(newProduct.getPrice());

        return productRepository.save(product);
    }

}
