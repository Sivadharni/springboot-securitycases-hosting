package com.example.springbootfirst.controllers;

import com.example.springbootfirst.models.AuthResponse;
import com.example.springbootfirst.models.RegisterDetails;
import com.example.springbootfirst.models.UserDetailsDto;
import com.example.springbootfirst.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    AuthService authService;

    @InjectMocks
    AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNewUser() {
        UserDetailsDto user = new UserDetailsDto();
        when(authService.addNewEmployee(user)).thenReturn("Employee Added Successfully");

        String result = authController.addNewUser(user);
        assertEquals("Employee Added Successfully", result);
    }

    @Test
    void login() {
        RegisterDetails login = new RegisterDetails();

        AuthResponse mockResponse = new AuthResponse("mock-token", "mock-user", Set.of("ROLE_USER"));
        when(authService.authenticate(login)).thenReturn(mockResponse);

        AuthResponse result = authController.login(login);
        assertEquals("mock-token", result.getToken());
        assertEquals("mock-user", result.getUsername());
    }
}
