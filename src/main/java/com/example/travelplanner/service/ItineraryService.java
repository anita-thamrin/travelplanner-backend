
package com.example.travelplanner.service;

import com.example.travelplanner.dto.ItineraryWithTotalDTO;
import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.entity.Trip;

import java.util.List;

public interface ItineraryService {
    Itinerary createItinerary(Itinerary itinerary);

    ItineraryWithTotalDTO getItinerary(Long id);

    List<Itinerary> getAllItineraries();

    void deleteItinerary(Long id);

    Trip addTripToItinerary(Long id, Trip trip);

    List<Trip> getTrips(Long id);

    Itinerary updateItinerary(Long id, Itinerary itinerary);
}
