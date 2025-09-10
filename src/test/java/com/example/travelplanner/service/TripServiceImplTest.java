package com.example.travelplanner.service;

import com.example.travelplanner.entity.Trip;
import com.example.travelplanner.exception.TripNotFoundException;
import com.example.travelplanner.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TripServiceImplTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripServiceImpl tripService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTrips() {
        Trip trip1 = new Trip();
        trip1.setId(1L);
        Trip trip2 = new Trip();
        trip2.setId(2L);

        when(tripRepository.findAll()).thenReturn(Arrays.asList(trip1, trip2));

        List<Trip> trips = tripService.getAllTrips();

        assertThat(trips).hasSize(2);
    }

    @Test
    public void testGetTripById_Success() {
        Trip trip = new Trip();
        trip.setId(1L);
        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));

        Optional<Trip> foundTrip = tripService.getTripById(1L);

        assertThat(foundTrip).isPresent();
        assertThat(foundTrip.get().getId()).isEqualTo(1L);
    }

    @Test
    public void testGetTripById_NotFound() {
        when(tripRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripService.getTripById(99L))
                .isInstanceOf(TripNotFoundException.class)
                .hasMessageContaining("Could not find Trip with id:99");
    }

    @Test
    public void testCreateTrip() {
        Trip trip = new Trip();
        trip.setActivityType("Hiking");
        when(tripRepository.save(trip)).thenReturn(trip);

        Trip createdTrip = tripService.createTrip(trip);

        assertThat(createdTrip.getActivityType()).isEqualTo("Hiking");
    }

    @Test
    public void testUpdateTrip_Success() {
        Trip existingTrip = new Trip();
        existingTrip.setId(1L);
        existingTrip.setActivityType("Old Activity");
        Trip newDetails = new Trip();
        newDetails.setActivityType("New Activity");

        when(tripRepository.findById(1L)).thenReturn(Optional.of(existingTrip));
        when(tripRepository.save(any(Trip.class))).thenReturn(existingTrip);

        Trip updatedTrip = tripService.updateTrip(1L, newDetails);

        assertThat(updatedTrip.getActivityType()).isEqualTo("New Activity");
    }

    @Test
    public void testUpdateTrip_NotFound() {
        when(tripRepository.findById(100L)).thenReturn(Optional.empty());

        Trip tripDetails = new Trip();

        assertThatThrownBy(() -> tripService.updateTrip(100L, tripDetails))
                .isInstanceOf(TripNotFoundException.class)
                .hasMessageContaining("Could not find Trip with id:100");
    }

    @Test
    public void testDeleteTrip_Success() {
        when(tripRepository.existsById(1L)).thenReturn(true);
        doNothing().when(tripRepository).deleteById(1L);

        tripService.deleteTrip(1L);

        verify(tripRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteTrip_NotFound() {
        when(tripRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> tripService.deleteTrip(999L))
                .isInstanceOf(TripNotFoundException.class)
                .hasMessageContaining("Could not find Trip with id:999");
    }

    @Test
    public void testGetTripsByDay() {
        Trip trip1 = new Trip();
        trip1.setTripDay(1);
        Trip trip2 = new Trip();
        trip2.setTripDay(1);

        when(tripRepository.findByTripDayOrderByTripDayAsc(1)).thenReturn(Arrays.asList(trip1, trip2));

        List<Trip> trips = tripService.getTripsByDay(1);

        assertThat(trips).hasSize(2);
    }
}
