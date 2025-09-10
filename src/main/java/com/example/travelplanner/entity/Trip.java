package com.example.travelplanner.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trip")
@Getter
@Setter
@NoArgsConstructor // Creates a no-argument constructor
@AllArgsConstructor // Creates a constructor with all arguments
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Day must not be null")
    @Min(value = 1, message = "Day must be within 1 to end of itinerary day")
    private Integer tripDay;

    @NotBlank(message = "Input activity type")
    @Size(max = 100, message = "Activity type must be less than or equal to 100 characters")
    private String activityType;

    @NotBlank(message = "Activity description cannot be blank")
    @Size(max = 500, message = "Activity description must be less than or equal to 500 characters")
    private String activityDesc;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be non-negative")
    private Double price;

    @JsonBackReference
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "itinerary_id", referencedColumnName = "id")
    private Itinerary itinerary;
}