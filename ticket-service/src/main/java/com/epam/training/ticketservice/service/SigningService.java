package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.UserDto;
import com.epam.training.ticketservice.dto.UserSessionDto;
import com.epam.training.ticketservice.entity.User;
import com.epam.training.ticketservice.entity.UserSession;
import com.epam.training.ticketservice.mapping.UserMapper;
import com.epam.training.ticketservice.mapping.UserSessionMapper;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.repository.UserSessionRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SigningService {
    private final UserSessionRepository userSessionRepository;
    private final UserRepository userRepository;
    private final UserSessionMapper userSessionMapper;
    private final UserMapper userMapper;

    @Transactional
    public String logInAsAdmin(String userName, String password) {
        if (isSomeoneLoggedIn()) {
            return "Someone is already logged in, please log out first!";
        }
        if (userName.equals("admin") && password.equals("admin")) {
            UserSessionDto userSessionDto = new UserSessionDto(userName);
            UserSession userSession = this.userSessionMapper.mapUserSessionDtoToEntity(userSessionDto);
            this.userSessionRepository.save(userSession);
            return "Signed in with privileged account 'admin'";
        }
        return "Login failed due to incorrect credentials";
    }

    public boolean isSomeoneLoggedIn() {
        return !this.userSessionRepository.findAll().isEmpty();
    }

    public String describeAccount() {
        if (!isSomeoneLoggedIn()) {
            return "You are not signed in";
        }
        var singedInUserEntity = this.userSessionRepository.findAll().get(0);
        String userName = singedInUserEntity.getUserName();
        if (userName.equals("admin")) {
            return String.format("Signed in with privileged account '%s'", userName);
        }
        return String.format("Signed in with account '%s'", userName);
    }

    @Transactional
    public String logInUser(String userName, String password) {
        var userOptional = this.userRepository.getUserByUserName(userName);
        if (userOptional.isEmpty()) {
            return String.format("There is no user signed up with username '%s'", userName);
        }
        User user = userOptional.get();
        if (!user.getPassword().equals(password)) {
            return "Login failed due to incorrect credentials";
        }

        if (isSomeoneLoggedIn()) {
            return "Someone is already logged in, please log out first!";
        }

        UserSessionDto userSessionDto = new UserSessionDto(userName);
        this.userSessionRepository.save(this.userSessionMapper.mapUserSessionDtoToEntity(userSessionDto));
        return String.format("Signed in with account '%s'", userName);
    }

    @Transactional
    public String signUpUser(String userName, String password) {
        UserDto userDto = new UserDto(userName, password);
        this.userRepository.save(this.userMapper.mapUserDtoToEntity(userDto));
        return String.format("User '%s' signed up successfully!", userName);
    }

    @Transactional
    public String signOutUser() {
        if (!isSomeoneLoggedIn()) {
            return "You cannot sign out, because no one is signed in!";
        }
        UserSession userSession = this.userSessionRepository.findAll().get(0);
        String userName = userSession.getUserName();

        this.userSessionRepository.deleteUserSessionByUserName(userName);
        return String.format("User '%s' sign out successfully", userName);
    }

    public boolean isAdminLoggedIn() {
        var userSessions = this.userSessionRepository.findAll();
        if (!userSessions.isEmpty()) {
            String loggedInUser = userSessions.get(0).getUserName();
            return loggedInUser.equals("admin");
        }
        return false;
    }

    public String getLoggedInUser() {
        var userOptional = this.userSessionRepository
                .findAll()
                .stream()
                .map(UserSession::getUserName)
                .findFirst();
        return userOptional.isEmpty() ? "" : userOptional.get();
    }
}
