package com.example.travelplanner.exception;

public class TripNotFoundException extends RuntimeException {
  public TripNotFoundException(Long id) {
    super("Could not find Trip with id:" + id);
  }

}
