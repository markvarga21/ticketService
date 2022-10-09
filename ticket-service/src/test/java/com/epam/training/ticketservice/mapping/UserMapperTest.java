package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.UserDto;
import com.epam.training.ticketservice.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private UserMapper underTest;

    @BeforeEach
    public void init() {
        this.underTest = new UserMapper(new ModelMapper());
    }

    @Test
    public void testMapUserDtoToEntity() {
        // Given
        String userName = "john";
        String password = "pass";
        User expected = new User(userName, password);
        UserDto userDto = new UserDto(userName, password);

        // When
        User actual = this.underTest.mapUserDtoToEntity(userDto);

        // Then
        assertEquals(expected, actual);
    }
}