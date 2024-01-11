package com.reviews.Directory.repository;

import com.reviews.Directory.entity_model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.productName LIKE %?1%" +
            "OR p.alternativeNames LIKE %?1%" +
            "OR p.brand LIKE %?1%" +
            "OR p.tags LIKE %?1%" +
            "OR p.description LIKE %?1%")
    public List<Product> findAll(String keyword);

}
