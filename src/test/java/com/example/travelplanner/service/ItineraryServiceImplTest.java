package com.example.travelplanner.service;

import com.example.travelplanner.dto.ItineraryWithTotalDTO;
import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.entity.Trip;
import com.example.travelplanner.exception.ItineraryNotFoundException;
import com.example.travelplanner.repository.ItineraryRepository;
import com.example.travelplanner.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ItineraryServiceImplTest {

    @Mock
    private ItineraryRepository itineraryRepository;
    @Mock
    private TripRepository tripRepository;
    @Mock
    private ItineraryServiceImpl itineraryService;

    @Mock
    private Itinerary itinerary;
    @Mock
    private Trip trip1;
    @Mock
    private Trip trip2;

    @BeforeEach
    void setUp() {
        itineraryRepository = Mockito.mock(ItineraryRepository.class);
        tripRepository = Mockito.mock(TripRepository.class);
        itineraryService = new ItineraryServiceImpl(itineraryRepository, tripRepository);
        itinerary = new Itinerary();
        itinerary.setId(1L);
        itinerary.setDestination("Paris");
        itinerary.setStartDate(LocalDate.of(2023, 10, 20));
        itinerary.setEndDate(LocalDate.of(2023, 10, 27));
        itinerary.setNotes("Trip to France");

        trip1 = new Trip();
        trip1.setId(10L);
        trip1.setItinerary(itinerary);
        trip1.setPrice(100.0);

        trip2 = new Trip();
        trip2.setId(11L);
        trip2.setItinerary(itinerary);
        trip2.setPrice(150.0);

        List<Trip> trips = new ArrayList<>();
        trips.add(trip1);
        trips.add(trip2);

        itinerary.setTrips(trips);
    }

    @Test
    void getItinerary_shouldReturnItineraryWithTotalDTO() {
        when(itineraryRepository.findById(1L)).thenReturn(Optional.of(itinerary));

        ItineraryWithTotalDTO result = itineraryService.getItinerary(1L);

        assertNotNull(result);
        // Corrected assertions to check the DTO's fields directly
        assertEquals(itinerary.getDestination(), result.getDestination());
        assertEquals(300.0, result.getTotalPrice());
        verify(itineraryRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowException_WhenItineraryNotFound() {
        when(itineraryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> itineraryService.getItinerary(1L))
                .isInstanceOf(ItineraryNotFoundException.class)
                .hasMessageContaining("Itinerary not found with ID");
    }

    @Test
    void shouldAddTripToItinerary() {
        Itinerary itinerary = new Itinerary();
        itinerary.setId(1L);
        itinerary.setTrips(new ArrayList<>());

        Trip trip = new Trip();
        trip.setTripDay(1);
        trip.setActivityType("Sightseeing");

        when(itineraryRepository.findById(1L)).thenReturn(Optional.of(itinerary));
        when(tripRepository.save(trip)).thenReturn(trip);

        Trip result = itineraryService.addTripToItinerary(1L, trip);

        assertThat(result).isEqualTo(trip);
        assertThat(trip.getItinerary()).isEqualTo(itinerary);
    }
}
