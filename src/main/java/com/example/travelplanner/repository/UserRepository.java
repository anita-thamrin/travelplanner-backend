package com.example.travelplanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.travelplanner.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Fuzzy search for first name
    List<User> findByFirstNameContainingIgnoreCase(String firstName);

    // Exact match for first name, case insensitive
    List<User> findByFirstNameIgnoreCase(String firstName);

}
