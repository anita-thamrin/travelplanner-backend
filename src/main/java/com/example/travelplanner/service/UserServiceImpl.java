package com.example.travelplanner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.entity.User;
import com.example.travelplanner.exception.UserNotFoundException;
import com.example.travelplanner.repository.ItineraryRepository;
import com.example.travelplanner.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ItineraryRepository itineraryRepository;

    public UserServiceImpl(UserRepository userRepository, ItineraryRepository itineraryRepository) {
        this.userRepository = userRepository;
        this.itineraryRepository = itineraryRepository;
    }

    // Create
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Read one
    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    // Read all
    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }

    // Update
    @Override
    public User updateUser(Long id, User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setContactNo(user.getContactNo());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setCountryOfOrigin(user.getCountryOfOrigin());

        return userRepository.save(userToUpdate);
    }

    // Delete
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findByFirstNameContainingIgnoreCase(String firstName) {
        List<User> foundUsers = userRepository.findByFirstNameContainingIgnoreCase(firstName);
        return foundUsers;
    }

    @Override
    public List<User> findByFirstNameIgnoreCase(String firstName) {
        List<User> foundUsers = userRepository.findByFirstNameIgnoreCase(firstName);
        return foundUsers;
    }

    @Override
    public Itinerary addItineraryToUser(Long id, Itinerary itinerary) {
        User selectedUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        itinerary.setUser(selectedUser);
        return itineraryRepository.save(itinerary);

    }

    @Override
    public List<Itinerary> getUserItineraries(Long id) {
        User selectedUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        List<Itinerary> itineraries = selectedUser.getItineraries();
        return itineraries;
    }

}
