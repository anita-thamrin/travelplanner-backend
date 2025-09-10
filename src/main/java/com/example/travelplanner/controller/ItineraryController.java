
package com.example.travelplanner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.travelplanner.dto.ItineraryWithTotalDTO;
import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.service.ItineraryService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/itineraries")
public class ItineraryController {
    private static final Logger logger = LoggerFactory.getLogger(ItineraryController.class);

    private final ItineraryService service;

    public ItineraryController(ItineraryService service) {
        this.service = service;
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Itinerary> updateItinerary(@PathVariable Long id, @RequestBody Itinerary itinerary) {
        logger.info("🟢 Updating itinerary with particular id");
        Itinerary updatedItinerary = service.updateItinerary(id, itinerary);
        return new ResponseEntity<>(updatedItinerary, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Itinerary> create(@Valid @RequestBody Itinerary itinerary) {
        logger.info("Received POST request to create new intinerary.");

        Itinerary createdItinerary = service.createItinerary(itinerary);

        logger.info("Successfully created a new itinerary with ID: {} ", createdItinerary.getId());
        return ResponseEntity.ok(createdItinerary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItineraryWithTotalDTO> get(@PathVariable Long id) {
        logger.info("Received GET request with ID: {}", id);

        ItineraryWithTotalDTO dto = service.getItinerary(id);
        logger.info("Retrieved itinerary: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<Itinerary>> all() {
        logger.info("Received GET request to get all itineraries");

        List<Itinerary> itineraries = service.getAllItineraries();
        logger.info("Successfully retrieved {} itineraries.", itineraries.size());
        return ResponseEntity.ok(itineraries);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Received DELETE request to delete itinerary with ID: {}", id);
        service.deleteItinerary(id);
        logger.info("Successfully deleted itinerary with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}

