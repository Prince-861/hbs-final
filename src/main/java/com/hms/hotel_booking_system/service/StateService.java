package com.hms.hotel_booking_system.service;

import com.hms.hotel_booking_system.entity.Country;
import com.hms.hotel_booking_system.entity.State;
import com.hms.hotel_booking_system.repository.CountryRepository;
import com.hms.hotel_booking_system.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateService {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    public StateService(StateRepository stateRepository, CountryRepository countryRepository) {
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
    }

    // Fetch all states
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    // Fetch state by ID
    public Optional<State> getStateById(Long id) {
        return stateRepository.findById(id);
    }

    // Add a new state with a valid country
    public State addState(State stateRequest) {
        if (stateRequest.getCountry() == null || stateRequest.getCountry().getId() == null) {
            throw new IllegalArgumentException("Country ID is required");
        }

        // Fetch Country from the database
        Country country = countryRepository.findById(stateRequest.getCountry().getId())
                .orElseThrow(() -> new RuntimeException("Country not found"));

        // Create and save State
        State state = new State();
        state.setName(stateRequest.getName());
        state.setCountry(country); // Set the managed country entity

        return stateRepository.save(state);
    }

    // Delete state by ID
    public void deleteState(Long id) {
        stateRepository.deleteById(id);
    }
}
