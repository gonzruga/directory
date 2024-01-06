package com.reviews.Directory.repository;

import com.reviews.Directory.entity_model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
