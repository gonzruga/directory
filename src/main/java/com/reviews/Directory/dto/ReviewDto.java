package com.reviews.Directory.dto;


import com.reviews.Directory.entity_model.Business;
import com.reviews.Directory.entity_model.Review;
import lombok.*;

import java.util.Date;

@Getter  //Data sometimes used for including objects
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long id;

    private String reviewContent;
    private String reviewWriterName;

    private Business reviewSubject; //Used in ReviewController: 'reviewSubmit'
    private Long reviewSubjectId;   //Todo state usage of reviewSubjectId

    private Date createdAt = new Date();
    private Date updatedAt;

    public Review dtoToReview() {
        final Review review = new Review();
        review.setId(id);
        review.setReviewContent(reviewContent);
        review.setReviewWriterName(reviewWriterName);

        review.setReviewSubject(reviewSubject);

        return review;
    }
}
