package com.hms.hotel_booking_system.repository;

import com.hms.hotel_booking_system.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByCityId(Long cityId);

    List<Property> findByStateId(Long stateId);

    @Query("select p from Property p JOIN p.city c JOIN p.state s JOIN s.country co WHERE c.name=:name OR s.name=:name OR co.name=:name")
    List<Property> searchHotels(@Param("name") String name);
}
