package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.service.BookingService;
import com.epam.training.ticketservice.service.SigningService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SigningCommandControllerTest {
    @InjectMocks
    private SigningCommandController underTest;

    @Mock
    private SigningService signingService;

    @Mock
    private BookingService bookingService;

    @Test
    void testSignInAdminShouldAllowWhenCorrectCredentials() {
        // Given
        String userName = "admin";
        String password = "admin";
        String expected = "Signed in with privileged account 'admin'";

        // When
        when(this.signingService.logInAsAdmin(userName, password))
                .thenReturn(expected);
        String actual = this.underTest.signInAdmin(userName, password);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void signInAsUser() {
    }

    @Test
    void signUpUser() {
    }

    @Test
    void describeAccount() {
    }

    @Test
    void signOut() {
    }
}