package com.example.travelplanner.service;

import com.example.travelplanner.entity.Trip;
import com.example.travelplanner.exception.TripNotFoundException;
import com.example.travelplanner.repository.TripRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public Optional<Trip> getTripById(Long id) {

        return Optional.ofNullable(tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(id)));
    }

    @Override
    @Transactional // Ensure transactional behavior for write operations
    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    @Transactional
    public Trip updateTrip(Long id, Trip tripDetails) {
        return tripRepository.findById(id)
                .map(trip -> {
                    trip.setActivityType(tripDetails.getActivityType());
                    trip.setActivityDesc(tripDetails.getActivityDesc());
                    trip.setPrice(tripDetails.getPrice());
                    return tripRepository.save(trip);
                }).orElseThrow(() -> new TripNotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteTrip(Long id) {
        if (!tripRepository.existsById(id)) {
            throw new TripNotFoundException(id);
        }
        tripRepository.deleteById(id);
    }

    @Override
    public List<Trip> getTripsByDay(Integer tripDay) {
        return tripRepository.findByTripDayOrderByTripDayAsc(tripDay);
    }

}
