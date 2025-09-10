package com.example.travelplanner.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.entity.Trip;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItineraryWithTotalDTO {
    private Long id;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;
    private List<Trip> trips;
    private double totalPrice;

    public ItineraryWithTotalDTO () {

    }

    public ItineraryWithTotalDTO(Itinerary itinerary, double totalPrice) {
        this.id = itinerary.getId();
        this.destination = itinerary.getDestination();
        this.startDate = itinerary.getStartDate();
        this.endDate = itinerary.getEndDate();
        this.notes = itinerary.getNotes();
        this.trips = itinerary.getTrips();
        this.totalPrice = totalPrice;
    }

}
