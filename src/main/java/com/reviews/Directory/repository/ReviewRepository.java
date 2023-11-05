package com.reviews.Directory.repository;

import com.reviews.Directory.entity_model.Business;
import com.reviews.Directory.entity_model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByReviewWriterName(String reviewWriterName);
    List<Review> findAllByReviewSubject_Id(Long id);

    List<Review> findAllByReviewSubject(Business business);

}
