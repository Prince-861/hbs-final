package com.hms.hotel_booking_system.controller;

import com.hms.hotel_booking_system.entity.Property;
import com.hms.hotel_booking_system.service.PropertyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/properties")
@CrossOrigin(origins = "*")  // Allows frontend to access this API
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    //  Get all properties
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        List<Property> properties = propertyService.getAllProperties();
        return properties.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(properties);
    }

    //  Get property by ID
    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //  Get properties by City ID
    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<Property>> getPropertiesByCity(@PathVariable Long cityId) {
        List<Property> properties = propertyService.getPropertiesByCity(cityId);
        return properties.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(properties);
    }

    //  Get properties by State ID
    @GetMapping("/state/{stateId}")
    public ResponseEntity<List<Property>> getPropertiesByState(@PathVariable Long stateId) {
        List<Property> properties = propertyService.getPropertiesByState(stateId);
        return properties.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(properties);
    }

    //  Add a new property
    @PostMapping
    public ResponseEntity<?> createProperty(@RequestBody Property propertyRequest) {
        try {
            Property savedProperty = propertyService.addProperty(propertyRequest);
            return ResponseEntity.ok(savedProperty);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
    }

    //  Delete property by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.ok("Property deleted successfully");
    }

    @GetMapping("/search-hotels")
    public List<Property> searchHotels(@RequestParam String name){
        return propertyService.searchHotels(name);
    }

}
