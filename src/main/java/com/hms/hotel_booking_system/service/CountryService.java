package com.hms.hotel_booking_system.service;

import com.hms.hotel_booking_system.entity.Country;
import com.hms.hotel_booking_system.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    // Fetch all countries
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    // Fetch country by ID
    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

    // Add a new country
    public Country addCountry(Country countryRequest) {
        if (countryRequest.getName() == null || countryRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Country name is required");
        }

        return countryRepository.save(countryRequest);
    }

    // Delete country by ID
    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }
}
