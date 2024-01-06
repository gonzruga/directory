package com.reviews.Directory.service;


import com.reviews.Directory.dto.ReviewDto;
import com.reviews.Directory.entity_model.Business;
import com.reviews.Directory.entity_model.Review;
import com.reviews.Directory.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    private final BusinessService businessService; //Added in order to connect review to a business.

// CREATE - POST
    public Review saveReview(ReviewDto review) {
        return repository.save(review.dtoToReview());
    }

    public List<Review>  saveReviews(List<Review> reviews) {
        return repository.saveAll(reviews);
    }

    //TODO: Revise code of SaveReviewDto
    public Review saveReviewDto(ReviewDto review) {
        try {
            Business business = businessService.getBusinessById(review.getReviewSubjectId());
            if (business != null){
                Review review1 = new Review();
                review1.setReviewSubject(business);
                review1.setReviewContent(review.getReviewContent());
                review1.setReviewWriterName(review.getReviewWriterName());
                return repository.save(review1);
            }else {
                throw new RuntimeException("Person Does Not Exist");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed To Save Review");
        }
    }


// READ - GET

    public List<Review> getReviews() {
        return repository.findAll();
    }

    //Todo: Assess if 'getReviews' method works and if needed. Other classes also
    public List<Review> getReviews(Long business) {
        return repository.findAllByReviewSubject_Id(business);
    }

    public Review getReviewById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Review getReviewByReviewWriterName(String reviewWriterName) {
        return repository.findByReviewWriterName(reviewWriterName);
        // This is a custom method. It is made in the Review repository.
    }

// DELETE
    public String deleteReview(long id){
//        repository.deleteAllById(Collections.singleton(id));
        repository.deleteById(id);
        return "Removed Review ID: " + id;
    }

// UPDATE - PUT
    public Review updateReview(ReviewDto review){
        Review existingReview = repository.findById(review.getId()).orElse(null);

        existingReview.setReviewContent(review.getReviewContent());
        existingReview.setReviewWriterName(review.getReviewWriterName());
        // Reviewer should not be changed
        // TODO Replace Writer Reviewer name automatically with user name then remove Writer Name edit

        existingReview.setUpdatedAt(new Date());

        return repository.save(existingReview);

    }


}
