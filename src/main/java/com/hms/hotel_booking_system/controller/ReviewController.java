package com.hms.hotel_booking_system.controller;

import com.hms.hotel_booking_system.entity.AppUser;
import com.hms.hotel_booking_system.entity.Review;
import com.hms.hotel_booking_system.service.PropertyService;
import com.hms.hotel_booking_system.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private PropertyService propertyService;
    private ReviewService reviewService;

    public ReviewController(PropertyService propertyService, ReviewService reviewService) {
        this.propertyService = propertyService;
        this.reviewService = reviewService;
    }


    @PostMapping
    public ResponseEntity<Review> write(@RequestBody Review review, //RequestBody sends the json content to the review java object
                                        @RequestParam long propertyId,
                                        @AuthenticationPrincipal AppUser user){

        Review write = reviewService.write(review, propertyId, user);
        return new ResponseEntity<>(write, HttpStatus.OK);

    }

    @GetMapping("/user/review")
    public ResponseEntity<List<Review>> getUserReviews(@AuthenticationPrincipal AppUser user){
        List<Review> reviews = reviewService.getUserReviews(user);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Optional<Review> updatedReview = reviewService.updateReview(id, review);

        if (updatedReview.isPresent()) {
            return ResponseEntity.ok(updatedReview.get());
        } else {
            return new ResponseEntity<>("Review does not exist for this Id",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        boolean deleted = reviewService.deleteReview(id);
        return deleted? new ResponseEntity<>("Review deleted successfully", HttpStatus.OK) : new ResponseEntity<>("Review does not exist for this Id", HttpStatus.NOT_FOUND);
    }
}
