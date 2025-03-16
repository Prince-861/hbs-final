package com.hms.hotel_booking_system.controller;

import com.hms.hotel_booking_system.entity.State;
import com.hms.hotel_booking_system.service.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    // Get all states
    @GetMapping
    public ResponseEntity<List<State>> getAllStates() {
        return ResponseEntity.ok(stateService.getAllStates());
    }

    // Get state by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStateById(@PathVariable Long id) {
        Optional<State> state = stateService.getStateById(id);
        return state.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new state
    @PostMapping
    public ResponseEntity<?> createState(@RequestBody State stateRequest) {
        try {
            State savedState = stateService.addState(stateRequest);
            return ResponseEntity.ok(savedState);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
    }

    // Delete state by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteState(@PathVariable Long id) {
        stateService.deleteState(id);
        return ResponseEntity.ok("State deleted successfully");
    }
}
