
package com.example.travelplanner.service;

import com.example.travelplanner.dto.ItineraryWithTotalDTO;
import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.entity.Trip;
import com.example.travelplanner.exception.ItineraryNotFoundException;
import com.example.travelplanner.repository.ItineraryRepository;
import com.example.travelplanner.repository.TripRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItineraryServiceImpl implements ItineraryService {

    private final ItineraryRepository repo;

    private TripRepository tripRepository;

    public ItineraryServiceImpl(ItineraryRepository repo, TripRepository tripRepository) {
        this.repo = repo;
        this.tripRepository = tripRepository;
    }

    @Override
    public Itinerary createItinerary(Itinerary itinerary) {
        return repo.save(itinerary);
    }

    @Override
    public ItineraryWithTotalDTO getItinerary(Long id) {
        Itinerary itinerary = repo.findById(id).orElseThrow(() -> new ItineraryNotFoundException(id));
        double totalPrice = itinerary.getTrips()
                .stream()
                .mapToDouble(Trip::getPrice)
                .sum();

        return new ItineraryWithTotalDTO(itinerary, totalPrice);
    }

    @Override
    public Itinerary updateItinerary(Long id, Itinerary itinerary) {
        Itinerary itineraryToUpdate = repo.findById(id)
                .orElseThrow(() -> new ItineraryNotFoundException(id));

        itineraryToUpdate.setDestination(itinerary.getDestination());
        itineraryToUpdate.setStartDate(itinerary.getStartDate());
        itineraryToUpdate.setEndDate(itinerary.getEndDate());
        itineraryToUpdate.setNotes(itinerary.getNotes());

        return repo.save(itineraryToUpdate);
    }

    @Override
    public List<Itinerary> getAllItineraries() {
        return repo.findAll();
    }

    @Override
    public void deleteItinerary(Long id) {
        if (!repo.existsById(id)) {
            throw new ItineraryNotFoundException(id);
        }
        repo.deleteById(id);
    }

    @Override
    public Trip addTripToItinerary(Long id, Trip trip) {
        Itinerary selectedItinerary = repo.findById(id).orElseThrow(() -> new ItineraryNotFoundException(id));

        trip.setItinerary(selectedItinerary);
        return tripRepository.save(trip);
    }

    @Override
    public List<Trip> getTrips(Long id) {
        Itinerary selectedItinerary = repo.findById(id).orElseThrow(() -> new ItineraryNotFoundException(id));
        List<Trip> trips = selectedItinerary.getTrips();
        return trips;
    }
}
