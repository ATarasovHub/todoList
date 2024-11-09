package com.example.todolist.controller;

import com.example.todolist.model.users.UsersRecord;
import com.example.todolist.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        // Reset any state before each test
    }

    @Test
    void testGetUsersByID_NonExistingID_ShouldReturn404NotFound() {
        // Arrange
        Long nonExistingUserId = 888L;
        when(userService.getUserById(nonExistingUserId)).thenReturn(null);

        // Act
        ResponseEntity<UsersRecord> responseEntity = userController.getUsersByID(nonExistingUserId);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Mockito.verify(userService, times(1)).getUserById(nonExistingUserId);
    }

    @Test
    void testLoginWithNullUsername_ShouldReturn401Unauthorized() {
        // Arrange
        UsersRecord loginRequest = UsersRecord.builder().build();
        loginRequest.setUsername(null);
        loginRequest.setPassword("validPassword");

        // Act
        ResponseEntity<UsersRecord> responseEntity = userController.login(loginRequest);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        Mockito.verify(userService, never()).authenticate(anyString(), anyString());
        Mockito.verify(userService, never()).findByUsername(anyString());
    }

    @Test
    void testLoginWithNullPassword_ShouldReturn401Unauthorized() {
        // Arrange
        UsersRecord loginRequest = UsersRecord.builder().build();

        loginRequest.setUsername("validUsername");
        loginRequest.setPassword(null);

        // Act
        ResponseEntity<UsersRecord> responseEntity = userController.login(loginRequest);

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        Mockito.verify(userService, never()).authenticate(anyString(), anyString());
        Mockito.verify(userService, never()).findByUsername(anyString());
    }
}
