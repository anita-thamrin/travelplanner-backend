package com.example.travelplanner.dto;

import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.entity.Trip;
import com.example.travelplanner.entity.User;
import com.example.travelplanner.repository.ItineraryRepository;
import com.example.travelplanner.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final UserRepository userRepository;
    private final ItineraryRepository itineraryRepository;

    public DataLoader(UserRepository userRepository, ItineraryRepository itineraryRepository) {
        this.userRepository = userRepository;
        this.itineraryRepository = itineraryRepository;
    }

    @PostConstruct
    @Transactional
    public void loadData() {
        logger.info("Starting data load...");

        // Clear existing data (consider cascade deletes if necessary)
        itineraryRepository.deleteAll();
        userRepository.deleteAll();

        // Create users
        User user1 = User.builder()
                .firstName("Nicole")
                .lastName("Kidman")
                .contactNo("12345678")
                .email("nicole.kidman@gmail.com")
                .countryOfOrigin("USA")
                .build();

        User user2 = User.builder()
                .firstName("Mona")
                .lastName("Lisa")
                .contactNo("12345678")
                .email("mona.lisa@gmail.com")
                .countryOfOrigin("Italy")
                .build();

        userRepository.saveAll(List.of(user1, user2));

        // Create itineraries for user1
        Itinerary itinerary1 = Itinerary.builder()
                .destination("Paris")
                .startDate(LocalDate.of(2025, 12, 1))
                .endDate(LocalDate.of(2025, 12, 10))
                .user(user1)
                .notes("Holiday in Paris")
                .build();

        Itinerary itinerary2 = Itinerary.builder()
                .destination("New York")
                .startDate(LocalDate.of(2026, 1, 5))
                .endDate(LocalDate.of(2026, 1, 15))
                .user(user1)
                .notes("Business trip")
                .build();

        itineraryRepository.saveAll(List.of(itinerary1, itinerary2));

        // Create trips for itinerary1
        Trip trip1 = new Trip();
        trip1.setTripDay(1);
        trip1.setActivityType("Sightseeing");
        trip1.setActivityDesc("Visit the Eiffel Tower");
        trip1.setPrice(50.0);
        trip1.setItinerary(itinerary1);

        Trip trip2 = new Trip();
        trip2.setTripDay(2);
        trip2.setActivityType("Museum");
        trip2.setActivityDesc("Louvre Museum visit");
        trip2.setPrice(30.0);
        trip2.setItinerary(itinerary1);

        itinerary1.setTrips(List.of(trip1, trip2));

        // Save itinerary1 again with trips (cascade persists if set)
        itineraryRepository.save(itinerary1);

        logger.info("Data loading completed.");
    }
}

// package com.example.travelplanner.dto;

// import org.springframework.stereotype.Component;

// import com.example.travelplanner.entity.User;
// import com.example.travelplanner.repository.UserRepository;

// import jakarta.annotation.PostConstruct;

// @Component
// public class DataLoader {
// private UserRepository userRepository;

// public DataLoader(UserRepository userRepository) {
// System.out.println("DataLoader: Constructor called.");
// this.userRepository = userRepository;
// }

// @PostConstruct
// public void loadData() {
// System.out.println("DataLoader: @PostContruct method started.");
// // Clear the database here
// userRepository.deleteAll();

// // Load data
// userRepository.save(User.builder().firstName("Nicole").lastName("Kidman").contactNo("12345678").email("nicole.kidman@gmail.com").countryOfOrigin("USA").build());
// userRepository.save(User.builder().firstName("Nicole").lastName("Changmin").contactNo("12345678").email("nicole.changmin@gmail.com").countryOfOrigin("Singapore").build());
// userRepository.save(User.builder().firstName("Mona").lastName("Lisa").contactNo("12345678").email("mona.lisa@gmail.com").countryOfOrigin("Italy").build());
// userRepository.save(User.builder().firstName("Peter").lastName("Parker").contactNo("12345678").email("peter.parker@gmail.com").countryOfOrigin("USA").build());
// userRepository.save(User.builder().firstName("Bruce").lastName("Wayne").contactNo("12345678").email("
// }
// }
