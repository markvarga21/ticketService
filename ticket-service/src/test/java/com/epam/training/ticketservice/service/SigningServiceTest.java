package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entity.User;
import com.epam.training.ticketservice.entity.UserSession;
import com.epam.training.ticketservice.mapping.UserMapper;
import com.epam.training.ticketservice.mapping.UserSessionMapper;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.repository.UserSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SigningServiceTest {
    @InjectMocks
    private SigningService underTest;
    @Mock
    private UserSessionRepository userSessionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserSessionMapper userSessionMapper;
    @Mock
    private UserMapper userMapper;


    @Test
    public void testLoginAsAdminShouldAllowWhenCorrectCredentials() {
        // Given
        String expected = "Signed in with privileged account 'admin'";
        UserSession dummyUserSession = new UserSession();
        String admin = "admin";
        dummyUserSession.setUserName(admin);

        // When
        when(this.userSessionMapper.mapUserSessionDtoToEntity(any()))
                .thenReturn(dummyUserSession);
        String actual = this.underTest.logInAsAdmin(admin, admin);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testLoginAsAdminShouldAllowWhenIncorrectCredentials() {
        // Given
        String dummyUserName = "dummy";
        String dummyUserPassword = "pass";
        String expected = "Login failed due to incorrect credentials";

        // When
        String actual = this.underTest.logInAsAdmin(dummyUserName, dummyUserPassword);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testLoginAsAdminShouldDenyWhenSomeoneIsLoggedIn() {
        // Given
        UserSession dummyUserSession = new UserSession();
        dummyUserSession.setUserName("dummy");
        String expected = "Someone is already logged in, please log out first!";

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of(dummyUserSession));
        String actual = this.underTest.logInAsAdmin("admin", "admin");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testIsSomeoneLoggedInShouldReturnTrueWhenSomeoneLoggedIn() {
        // Given
        boolean expected = true;
        UserSession dummyUserSession = new UserSession();
        dummyUserSession.setUserName("dummy");

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of(dummyUserSession));
        boolean actual = this.underTest.isSomeoneLoggedIn();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testIsSomeoneLoggedInShouldReturnFalseWhenNobodyIsLoggedIn() {
        // Given
        boolean expected = false;

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of());
        boolean actual = this.underTest.isSomeoneLoggedIn();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testDescribeAccountWhenNobodyIsLoggedIn() {
        // Given
        String expected = "You are not signed in";

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of());
        String actual = this.underTest.describeAccount();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testDescribeAccountWhenAdminIsLoggedIn() {
        // Given
        String expected = "Signed in with privileged account 'admin'";
        UserSession adminUserSession = new UserSession();
        adminUserSession.setUserName("admin");

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of(adminUserSession));
        String actual = this.underTest.describeAccount();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testDescribeAccountWhenSomeoneIsLoggedIn() {
        // Given
        String userName = "john";
        String expected = String.format("Signed in with account '%s'", userName);
        UserSession userSession = new UserSession();
        userSession.setUserName(userName);

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of(userSession));
        String actual = this.underTest.describeAccount();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testLogInUserWhenIsNotSignedUp() {
        // Given
        String username = "john";
        String password = "pass";
        String expected = String.format("There is no user signed up with username '%s'", username);

        // When
        when(this.userRepository.getUserByUserName(username))
                .thenReturn(Optional.empty());
        String actual = this.underTest.logInUser(username, password);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testLogInUserWhenIncorrectCredentials() {
        // Given
        String username = "john";
        String password = "pass";
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        String wrongPassword = "wrong";
        String expected = "Login failed due to incorrect credentials";

        // When
        when(this.userRepository.getUserByUserName(username))
                .thenReturn(Optional.of(user));
        String actual = this.underTest.logInUser(username, wrongPassword);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testLogInUserWhenSomeoneIsSignedIn() {
        // Given
        String userName = "john";
        String passWord = "pass";
        UserSession dummyUserSession = new UserSession();
        dummyUserSession.setUserName(userName);
        User dummyUser = new User();
        dummyUser.setUserName(userName);
        dummyUser.setPassword(passWord);
        String expected = "Someone is already logged in, please log out first!";

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of(dummyUserSession));
        when(this.userRepository.getUserByUserName(userName))
                .thenReturn(Optional.of(dummyUser));
        String actual = underTest.logInUser(userName, "pass");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testLogInUserWhenCorrectCredentials() {
        // Given
        String expected = "Login successful!";
        String userName = "john";
        String passWord = "pass";
        User dummyUser = new User();
        dummyUser.setUserName(userName);
        dummyUser.setPassword(passWord);

        // When
        when(this.userRepository.getUserByUserName(userName))
                .thenReturn(Optional.of(dummyUser));
        String actual = this.underTest.logInUser(userName, passWord);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testSignUpUser() {
        // Given
        String userName = "john";
        String password = "pass";
        String expected = String.format("User '%s' signed up successfully!", userName);

        // When
        String actual = this.underTest.signUpUser(userName, password);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testSignOutUserWhenNobodyIsSignedIn() {
        // Given
        String expected = "You cannot sign out, because no one is signed in!";

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of());
        String actual = this.underTest.signOutUser();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testSignOutUser() {
        // Given
        String userName = "john";
        String password = "pass";
        String expected = String.format("User '%s' sign out successfully", userName);
        UserSession userSession = new UserSession();
        userSession.setUserName(userName);

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of(userSession));
        String actual = this.underTest.signOutUser();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testIsAdminLoggedInShouldReturnFalseWhenNotLoggedIn() {
        // Given
        boolean expected = false;

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of());
        boolean actual = this.underTest.isAdminLoggedIn();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testIsAdminLoggedInShouldReturnTrueWhenLoggedIn() {
        // Given
        boolean expected = true;
        UserSession userSession = new UserSession();
        userSession.setUserName("admin");

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of(userSession));
        boolean actual = this.underTest.isAdminLoggedIn();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetLoggedInUserWhenEmpty() {
        // Given
        String expected = "";

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of());
        String actual = this.underTest.getLoggedInUser();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetLoggedInUser() {
        // Given
        String expected = "john";
        UserSession userSession = new UserSession();
        userSession.setUserName("john");

        // When
        when(this.userSessionRepository.findAll())
                .thenReturn(List.of(userSession));
        String actual = this.underTest.getLoggedInUser();

        // Then
        assertEquals(expected, actual);
    }
}