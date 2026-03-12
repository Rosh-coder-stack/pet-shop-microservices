package com.petshop.product.service;

import com.petshop.product.model.Product;
import com.petshop.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product reduceStock(Long productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Out of stock");
        }

        product.setStock(product.getStock() - quantity);

        return productRepository.save(product);
    }
}