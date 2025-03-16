package com.hms.hotel_booking_system.repository;

import com.hms.hotel_booking_system.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StateRepository extends JpaRepository<State, Long> {
}