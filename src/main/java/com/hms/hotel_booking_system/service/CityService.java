package com.hms.hotel_booking_system.service;

import com.hms.hotel_booking_system.entity.City;
import com.hms.hotel_booking_system.entity.State;
import com.hms.hotel_booking_system.repository.CityRepository;
import com.hms.hotel_booking_system.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public CityService(CityRepository cityRepository, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    // Fetch all cities
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    // Fetch city by ID
    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    // Add a new city with a valid state
    public City addCity(City cityRequest) {
        if (cityRequest.getState() == null || cityRequest.getState().getId() == null) {
            throw new IllegalArgumentException("State ID is required");
        }

        // Fetch State from the database
        State state = stateRepository.findById(cityRequest.getState().getId())
                .orElseThrow(() -> new RuntimeException("State not found"));

        // Create and save City
        City city = new City();
        city.setName(cityRequest.getName());
        city.setState(state); // Set the managed state entity

        return cityRepository.save(city);
    }

    // Delete city by ID
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }
}
