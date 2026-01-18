package com.petshop.product.repository;
import com.petshop.product.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository< Product, Long> {
}
