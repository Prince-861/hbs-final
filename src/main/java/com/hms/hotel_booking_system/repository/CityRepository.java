package com.hms.hotel_booking_system.repository;

import com.hms.hotel_booking_system.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CityRepository extends JpaRepository<City, Long> {

}