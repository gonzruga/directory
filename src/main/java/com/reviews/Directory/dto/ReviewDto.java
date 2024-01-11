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

    private Business reviewSubject; //Used in Controller - review Submit
    private Long reviewSubjectId;   //Todo Review & confirm usage of reviewSubjectId

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

/*

{
"id" : "1"
"reviewContent" : "XXX Limited is a great company",
"reviewWriterName" : "John"

"review_subject_id" : "1"

 "created_at":"2023-07-24 10:10:20",
 "updated_at":"2023-07-27 10:10:20"
        }

 */
