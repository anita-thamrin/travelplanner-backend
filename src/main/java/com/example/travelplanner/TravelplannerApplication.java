package com.example.travelplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelplannerApplication {

	public static void main(String[] args) {
		System.out.println("Starting application.....");
		SpringApplication.run(TravelplannerApplication.class, args);
		System.out.println("Application has started");
	}

}
