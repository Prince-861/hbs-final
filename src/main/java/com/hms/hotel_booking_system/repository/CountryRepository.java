package com.hms.hotel_booking_system.repository;

import com.hms.hotel_booking_system.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}