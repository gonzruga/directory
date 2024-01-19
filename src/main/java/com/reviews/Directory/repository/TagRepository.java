package com.reviews.Directory.repository;

import com.reviews.Directory.entity_model.Product;
import com.reviews.Directory.entity_model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t WHERE t.tagTitle LIKE %?1%" +
            "OR t.tagDescription LIKE %?1%")
    public List<Tag> findAll(String keyword);

}
