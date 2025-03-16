package com.hms.hotel_booking_system.controller;

import com.hms.hotel_booking_system.entity.AppUser;
import com.hms.hotel_booking_system.entity.Country;
import com.hms.hotel_booking_system.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    // Get all countries
    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    // Get country by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable Long id) {
        Optional<Country> country = countryService.getCountryById(id);
        return country.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new country
    @PostMapping
    public ResponseEntity<?> createCountry(@RequestBody Country countryRequest) {
        try {
            Country savedCountry = countryService.addCountry(countryRequest);
            return ResponseEntity.ok(savedCountry);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
    }

    // Delete country by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.ok("Country deleted successfully");
    }

    @PostMapping("/addCountry")
    public AppUser addCountry(@AuthenticationPrincipal AppUser user){

        return user;
    }
}
