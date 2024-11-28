package com.reviews.Directory.repository;

import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.entity_model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p left join p.tagList t WHERE p.productName LIKE %?1%" +
            "OR p.alternativeNames LIKE %?1%" +
            "OR p.brand LIKE %?1%" +
            "OR p.description LIKE %?1%" +

            "OR t.tagTitle LIKE %?1%" +
            "OR t.tagDescription LIKE %?1%" +
            "ORDER BY p.sponsorLevel DESC, FUNCTION('RAND')"  // Randomize the order
//            "ORDER BY RAND()",
//            nativeQuery = true

    )
    public List<Product> findAllRandomOrder(String keyword);
//    List<Product> findAll(String keyword);

    @Query("SELECT p FROM Product p ORDER BY p.sponsorLevel DESC, FUNCTION('RAND')")
    List<Product> findAllRandomOrder();

}
