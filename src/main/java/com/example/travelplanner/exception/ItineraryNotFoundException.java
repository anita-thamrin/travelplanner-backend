
package com.example.travelplanner.exception;

public class ItineraryNotFoundException extends RuntimeException {
    public ItineraryNotFoundException(Long id) {
        super("Itinerary not found with ID: " + id);
    }
}
