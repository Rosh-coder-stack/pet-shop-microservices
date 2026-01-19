package com.petshop.product.repository;
import com.petshop.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProductRepository extends JpaRepository< Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);

}
