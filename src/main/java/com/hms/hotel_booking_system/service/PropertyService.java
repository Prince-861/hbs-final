package com.hms.hotel_booking_system.service;

import com.hms.hotel_booking_system.entity.City;
import com.hms.hotel_booking_system.entity.Property;
import com.hms.hotel_booking_system.entity.State;
import com.hms.hotel_booking_system.repository.CityRepository;
import com.hms.hotel_booking_system.repository.PropertyRepository;
import com.hms.hotel_booking_system.repository.StateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public PropertyService(PropertyRepository propertyRepository,
                           CityRepository cityRepository,
                           StateRepository stateRepository) {
        this.propertyRepository = propertyRepository;
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    //  Get all properties
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    //  Get property by ID
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    //  Get properties by City ID
    public List<Property> getPropertiesByCity(Long cityId) {
        return propertyRepository.findByCityId(cityId);
    }

    //  Get properties by State ID
    public List<Property> getPropertiesByState(Long stateId) {
        return propertyRepository.findByStateId(stateId);
    }

    //  Add a new property
    @Transactional
    public Property addProperty(Property propertyRequest) {
        if (propertyRequest.getName() == null || propertyRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Property name is required");
        }
        if (propertyRequest.getState() == null || propertyRequest.getState().getId() == null) {
            throw new IllegalArgumentException("State ID is required");
        }
        if (propertyRequest.getCity() == null || propertyRequest.getCity().getId() == null) {
            throw new IllegalArgumentException("City ID is required");
        }

        // Fetch full City and State objects from DB
        City city = cityRepository.findById(propertyRequest.getCity().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid city ID"));

        State state = stateRepository.findById(propertyRequest.getState().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid state ID"));

        // Set the fetched objects
        propertyRequest.setCity(city);
        propertyRequest.setState(state);

        return propertyRepository.save(propertyRequest);
    }

    //  Delete property by ID
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new IllegalArgumentException("Property not found with ID: " + id);
        }
        propertyRepository.deleteById(id);
    }

    public List<Property> searchHotels(String name) {
        List<Property> properties = propertyRepository.searchHotels(name);
        return properties;
    }
}
