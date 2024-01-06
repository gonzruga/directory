package com.reviews.Directory.controller;

import com.reviews.Directory.dto.ReviewDto;
import com.reviews.Directory.entity_model.Review;
import com.reviews.Directory.service.BusinessService;
import com.reviews.Directory.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController  // Data
@Slf4j
@Controller // For attaching FORM since 'RestController' does not.
//@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewController {

    @Autowired
    private ReviewService service;

    private final BusinessService businessService;

// CREATE - POST

    @GetMapping("/reviewCreate/{businessId}")
    public String reviewParam1(
            @PathVariable(name="businessId") Long businessId,
            Model theModel
    ) {
        Review theReview = new Review();
        theModel.addAttribute("businessId", businessId);
        theModel.addAttribute("review", theReview);  // Name & value of attribute.
        return "review-form";
    }

    @PostMapping("/reviewSubmit") // /{id}
    public String reviewSubmit(@ModelAttribute ReviewDto review, Model model, long id) {
        model.addAttribute("theReview", review);  // Name & value of attribute.
        review.setReviewSubject(businessService.getBusinessById(id));
        log.info("{}",id);
        review.setReviewSubjectId(id);
        service.saveReview(review);
        return "review-submit";
    }

// READ - GET*
    @GetMapping("/review/{id}")
    public Review findReviewById(@PathVariable long id){
        return service.getReviewById(id);
    }

    @GetMapping("/reviewPage/{id}")
    public String reviewPage(@PathVariable long id, Model model){
        Review reviewById = service.getReviewById(id);
        model.addAttribute("review", reviewById);
        return "review-page";
    }


    // UPDATE - PUT
    @GetMapping("/reviewEdit/{reviewId}")
    public String editReview(@PathVariable long reviewId, Model model){
    Review review = new Review();
    review = service.getReviewById(reviewId);
    model.addAttribute("review", review);
    return "review-edit";  //Then directed to 'updateReview/{id}'
    }

    @PostMapping("/reviewUpdate/{id}") // TODO Debug this method
    public String updateReview(@ModelAttribute ReviewDto review, @PathVariable long id) {
        service.updateReview(review);
        return "redirect:/businessList";
    }

    //Temporary method while debugging Update method. This method doesn't do anything, just redirects to directory
    @PostMapping("/updateReviewTemp")
    public String updateReviewTemp(@ModelAttribute ReviewDto review) {
    //service.updateReview(review);
        return "redirect:/businessList"; // TODO Direct back to page of the business that review belonged to.
    }

// DELETE
    @GetMapping("/reviewDelete/{id}")
    public String deleteReview(@PathVariable long id){
        service.deleteReview(id);
    return "redirect:/businessList";
}

    @GetMapping("/reviewDelete1/{id}")
    public String deleteReview1(@PathVariable long id){
        return service.deleteReview(id);
    }








// UNUSED METHODS -----------------------------------

    // CREATE
    @PostMapping("/addRevRE")
    public ResponseEntity<Review> addReview1(@RequestBody ReviewDto review) {
        return new ResponseEntity<>(service.saveReview(review), HttpStatus.OK) ;
    }

    @PostMapping("/addRevData") // Data
    public Review addReview2(@RequestBody ReviewDto review) {
        return service.saveReview(review);
    }

    @PostMapping("/addReviews")
    public List<Review> addReviews(@RequestBody List<Review> reviews) {
        return service.saveReviews(reviews);
    }

    // READ - GET*
    @GetMapping("/reviews1")
    public ResponseEntity<List<Review>> findAllByReviews(){
        return ResponseEntity.ok(service.getReviews());
    }
    @GetMapping("/reviews2")
    public List<Review> findAllReviews2(){
        return service.getReviews();
    }

    @GetMapping("/rwname/{reviewWriterName}")
    public  Review findReviewByReviewWriterName(@PathVariable String reviewWriterName){
        return service.getReviewByReviewWriterName(reviewWriterName);
    }

    @GetMapping("/singleBizReviews/{id}")
    public ResponseEntity<List<Review>> findAllByReviewSubject_Id(@PathVariable Long id){
        return ResponseEntity.ok(service.getReviews(id));
    }



}