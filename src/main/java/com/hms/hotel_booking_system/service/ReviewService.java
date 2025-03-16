package com.hms.hotel_booking_system.service;

import com.hms.hotel_booking_system.entity.AppUser;
import com.hms.hotel_booking_system.entity.Property;
import com.hms.hotel_booking_system.entity.Review;
import com.hms.hotel_booking_system.exception.ResourceAlreadyExistException;
import com.hms.hotel_booking_system.payload.AppUserDto;
import com.hms.hotel_booking_system.repository.PropertyRepository;
import com.hms.hotel_booking_system.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private PropertyRepository propertyRepository;

    private ReviewRepository reviewRepository;


    public ReviewService(PropertyRepository propertyRepository, ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.reviewRepository = reviewRepository;
    }

    public Review write(Review review, Long propertyId, AppUser user){
        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        if(opProperty.isPresent()){
            Property property = opProperty.get();
            if(reviewRepository.existsByAppUserAndProperty(user,property)){
                throw new ResourceAlreadyExistException("Review already exists for this user and property");
            }
            review.setProperty(property);
            review.setAppUser(user);
            Review savedReview = reviewRepository.save(review);
            return savedReview;
        }
        else{
            throw new ResourceAlreadyExistException("Property not found");
        }
    }

    public List<Review> getUserReviews(AppUser user) {
        List<Review> reviews = reviewRepository.findByAppUser(user);
        return reviews;
    }

    public Optional<Review> updateReview(Long id, Review updatedReview) {
        Optional<Review> existingReview = reviewRepository.findById(id);

        if (existingReview.isPresent()) {
            Review review = existingReview.get();

            if (updatedReview.getRating() != null) {
                review.setRating(updatedReview.getRating());
            }
            if (updatedReview.getDescription() != null) {
                review.setDescription(updatedReview.getDescription());
            }
            if (updatedReview.getAppUser() != null) {
                review.setAppUser(updatedReview.getAppUser());
            }
            if (updatedReview.getProperty() != null) {
                review.setProperty(updatedReview.getProperty());
            }

            return Optional.of(reviewRepository.save(review));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteReview(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
