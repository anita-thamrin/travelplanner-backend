package com.example.travelplanner.controller;

import com.example.travelplanner.entity.Trip;
import com.example.travelplanner.service.TripService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trips")
public class TripController {

    private static final Logger logger = LoggerFactory.getLogger(TripController.class);

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        logger.info("Received GET request to get all trips");

        List<Trip> trips = tripService.getAllTrips();
        logger.info("Successfully retrieved {} trips.", trips.size());
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        logger.info("Received GET request to get trip ID: " + id);
        Optional<Trip> tripOptional = tripService.getTripById(id);

        return tripOptional.map(trip -> {
            logger.info("Found trip with ID {}: {}", id, trip);
            return ResponseEntity.ok(trip);
        }).orElseGet(() -> {
            logger.info("Trip with ID {} not found.", id);
            return ResponseEntity.notFound().build();
        });
    }

    @PostMapping
    public ResponseEntity<Trip> createTrip(@Valid @RequestBody Trip trip) {
        logger.info("Received POST request to create a new trip.");

        Trip createdTrip = tripService.createTrip(trip);
        logger.info("Successfully created a new trip with ID: {}.", createdTrip.getId());
        return new ResponseEntity<>(createdTrip, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @Valid @RequestBody Trip tripDetails) {
        logger.info("Received PUT request to update trip ID: {}", id);

        Trip updatedTrip = tripService.updateTrip(id, tripDetails);
        logger.info("Successfully updated trip ID: {}", updatedTrip.getId());
        return ResponseEntity.ok(updatedTrip);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        logger.info("Received DELETE request to delete trip ID: {}", id);

        tripService.deleteTrip(id);
        logger.info("Successfully deleted trip ID: {}.", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/day/{tripDay}")
    public ResponseEntity<List<Trip>> getTripsByDay(@PathVariable Integer tripDay) {
        logger.info("Received GET request to get all trips for day: {}", tripDay);

        List<Trip> trips = tripService.getTripsByDay(tripDay);

        logger.info("Successfully retrieved {} trips for day {}", trips.size(), tripDay);
        return ResponseEntity.ok(trips);
    }
}
