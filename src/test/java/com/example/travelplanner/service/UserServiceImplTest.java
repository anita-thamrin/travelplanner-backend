package com.example.travelplanner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.travelplanner.entity.User;
import com.example.travelplanner.exception.UserNotFoundException;
import com.example.travelplanner.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void createUserTest() {
    // ARRANGE
    // Create a new user
    User user = User.builder().firstName("Clint").lastName("Barton").email("clint@a.com")
        .contactNo("12345678").countryOfOrigin("USA").build();

    // Mock the save method of the userRepository
    when(userRepository.save(user)).thenReturn(user);

    // ACT
    User savedUser = userService.createUser(user);

    // ASSERT
    assertEquals(user, savedUser, "The saved user should be the same as the new user");

    // Also verify that the save method of customerRepository is called once
    verify(userRepository, times(1)).save(user);

  }

    @Test
    public void testGetUser() {
    // ARRANGE
    User user = User.builder().firstName("Clint").lastName("Barton").email("clint@a.com")
        .contactNo("12345678").countryOfOrigin("USA").build();

    Long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    // ACT
    User retrievedUser = userService.getUser(userId);

    // ASSERT
    assertEquals(user, retrievedUser, "The retrieved user should match the saved user");
  }

    @Test
    public void testGetUserNotFound() {
    // ARRANGE
    Long userId = 1L;
    // Mock retrieving a customer that does not exist
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    // ACT & ASSERT
    assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
  }
}
