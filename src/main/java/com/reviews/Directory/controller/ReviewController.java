package com.reviews.Directory.controller;

import com.reviews.Directory.dto.ReviewDto;
import com.reviews.Directory.entity_model.Review;
import com.reviews.Directory.service.BusinessService;
import com.reviews.Directory.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController  // Data
@Slf4j
@Controller // For attaching FORM since 'RestController' does not.
@AllArgsConstructor
public class ReviewController {
    private final BusinessService businessService;
    private ReviewService service;

// -----  START OF FORMS AND PAGES  ------------------------------------------

    @GetMapping("/reviewCreate/{businessId}")
    public String reviewParam1(
            @PathVariable(name="businessId") Long businessId,
            Model theModel
    ) {
        Review theReview = new Review();
        theModel.addAttribute("businessId",businessId);
        theModel.addAttribute("review", theReview);  // Name & value of attribute.
        return "review-form";
    }

    @PostMapping("/reviewSubmit") // /{id}
    public String reviewSubmit(@ModelAttribute ReviewDto theReview, Model theModel,long id) {
        theModel.addAttribute("theReview", theReview);  // Name & value of attribute.
        theReview.setReviewSubject(businessService.getBusinessById(id));
        log.info("{}",id);
        theReview.setReviewSubjectId(id);
        service.saveReview(theReview);
        return "review-submit";
    }


// ----- END OF FORMS AND PAGES ---------------------------------------------


//-----START OF CRUD FUNCTIONS ---------------------------------------------

// CREATE - POST
    @PostMapping("/addRevRE")
    public ResponseEntity<Review> addReview1(@RequestBody ReviewDto review) {
        return new ResponseEntity<>(service.saveReview(review), HttpStatus.OK) ;
    }
    @PostMapping("/addRevData") // Data
    public Review addReview2(@RequestBody ReviewDto review) {
        return service.saveReview(review);   }

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
    public List<Review> findAllReviews2(){return service.getReviews(); }

    @GetMapping("/review/{id}")
    public  Review findReviewById(@PathVariable long id){
        return service.getReviewById(id);
    }

    @GetMapping("/rwname/{reviewWriterName}")
    public  Review findReviewByReviewWriterName(@PathVariable String reviewWriterName){
        return service.getReviewByReviewWriterName(reviewWriterName);
    }

    @GetMapping("/singleBizReviews/{id}")
    public ResponseEntity<List<Review>> findAllByReviewSubject_Id(@PathVariable Long id){
        return ResponseEntity.ok(service.getReviews(id));
    }

// UPDATE - PUT
    @GetMapping("/reviewEdit/{revId}")
    public String editReview(@PathVariable long revId, Model model){
    Review review = new Review();
    review = service.getReviewById(revId);
    model.addAttribute("review", review);
    return "review-edit";  //Then directed to 'updateReview/{id}'
}

//Temporary method while debugging Update method. This method doesn't do anything'
    @PostMapping("/updateReviewTemp")
    public String updateReviewTemp(@ModelAttribute ReviewDto review) {
//        service.updateReview(review);
        return "redirect:/businessList";
    }

    @PostMapping("/updateReview/{revId}") //TODO Debug this method
    public String updateReview(@PathVariable long revId, @ModelAttribute ReviewDto review) {
        service.updateReview(review);
        return "redirect:/businessList"; // TODO Direct back to page of the business that review belongs to.
    }

// DELETE
    @DeleteMapping("/deleteR/{id}")
    public String deleteReview(@PathVariable long id){
        return service.deleteReview(id);
    }






}