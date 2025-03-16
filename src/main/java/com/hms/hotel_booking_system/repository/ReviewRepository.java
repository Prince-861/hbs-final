package com.hms.hotel_booking_system.repository;

import com.hms.hotel_booking_system.entity.AppUser;
import com.hms.hotel_booking_system.entity.Property;
import com.hms.hotel_booking_system.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //findByUserId
    List<Review> findByAppUser(AppUser user);

    //findByUserAndProperty
    boolean existsByAppUserAndProperty(AppUser user, Property property);
}