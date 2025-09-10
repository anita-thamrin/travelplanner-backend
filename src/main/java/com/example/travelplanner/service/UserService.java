package com.example.travelplanner.service;

import java.util.List;

import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.entity.User;

public interface UserService {
    User createUser(User user);

    User getUser(Long id);

    List<User> getAllUsers();

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    List<User> findByFirstNameContainingIgnoreCase(String firstName);

    List<User> findByFirstNameIgnoreCase(String firstName);

    Itinerary addItineraryToUser(Long id, Itinerary itinerary);
    List<Itinerary> getUserItineraries(Long id);

}
