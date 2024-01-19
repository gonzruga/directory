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
            "OR t.tagDescription LIKE %?1%"

            //   List< Tag > tagList;
        //   tagTitle;
        //   tagDescription;


    )
    public List<Product> findAll(String keyword);

}
