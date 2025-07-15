package com.example.springbootfirst.services;

import com.example.springbootfirst.models.AuthResponse;
import com.example.springbootfirst.models.RegisterDetails;
import com.example.springbootfirst.models.UserDetailsDto;
import com.example.springbootfirst.repository.RegisterDetailsRepository;
import com.example.springbootfirst.repository.RegisterRepository;
import com.example.springbootfirst.repository.RolesRepository;
import com.example.springbootfirst.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    RegisterRepository registerRepository;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    RegisterDetailsRepository registerDetailsRepository;
    @Mock
    RolesRepository rolesRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNewEmployee() {
        UserDetailsDto user = new UserDetailsDto();
        user.setRoleNames(Set.of("ROLE_USER"));
        when(rolesRepository.findByRoleName("ROLE_USER")).thenReturn(Optional.empty());
        // Since logic uses exception, we just check it throws error
        assertThrows(RuntimeException.class, () -> authService.addNewEmployee(user));
    }

    @Test
    void authenticate() {
        RegisterDetails login = new RegisterDetails();
        login.setUserName("testuser");
        login.setPassword("pass");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("mock-token");

        RegisterDetails user = new RegisterDetails();
        user.setUserName("testuser");
        user.setRoles(Set.of());
        when(registerRepository.findByUserName("testuser")).thenReturn(Optional.of(user));

        AuthResponse response = authService.authenticate(login);
        assertNotNull(response);
        assertEquals("mock-token", response.getToken());
    }

    @Test
    void getUserByUsername() {
        RegisterDetails user = new RegisterDetails();
        user.setUserName("testuser");
        when(registerRepository.findByUserName("testuser")).thenReturn(Optional.of(user));

        assertTrue(authService.getUserByUsername("testuser").isPresent());
    }
}
