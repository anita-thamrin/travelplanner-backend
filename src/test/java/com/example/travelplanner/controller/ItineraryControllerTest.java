
package com.example.travelplanner.controller;

import com.example.travelplanner.entity.Itinerary;
import com.example.travelplanner.service.ItineraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItineraryController.class)
@Import(ItineraryControllerTest.MockServiceConfig.class)
public class ItineraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockServiceConfig {
        @Bean
        public ItineraryService itineraryService() {
            return Mockito.mock(ItineraryService.class);
        }
    }

    @Test
    void testCreateItinerary_ValidationError() throws Exception {
        Itinerary itinerary = new Itinerary();
        itinerary.setNotes("Only notes provided");

        mockMvc.perform(post("/itineraries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itinerary)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("destination")));
    }

    @Test
    void testCreateItinerary_Success() throws Exception {
        Itinerary itinerary = new Itinerary();
        itinerary.setDestination("Rome");
        itinerary.setStartDate(LocalDate.of(2025, 10, 1));
        itinerary.setEndDate(LocalDate.of(2025, 10, 10));
        itinerary.setNotes("Family trip");

        when(itineraryService.createItinerary(any(Itinerary.class))).thenReturn(itinerary);

        mockMvc.perform(post("/itineraries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itinerary)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.destination").value("Rome"));
    }
}
