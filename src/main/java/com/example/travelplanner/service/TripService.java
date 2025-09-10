package com.example.travelplanner.service;

import com.example.travelplanner.entity.Trip;
import java.util.List;
import java.util.Optional;

public interface TripService {

    List<Trip> getAllTrips();

    Optional<Trip> getTripById(Long id);

    Trip createTrip(Trip trip);

    Trip updateTrip(Long id, Trip tripDetails);

    void deleteTrip(Long id);

    List<Trip> getTripsByDay(Integer day);
}
