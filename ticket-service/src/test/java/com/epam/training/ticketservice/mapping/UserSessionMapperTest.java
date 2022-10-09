package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.UserSessionDto;
import com.epam.training.ticketservice.entity.UserSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class UserSessionMapperTest {
    private UserSessionMapper underTest;

    @BeforeEach
    public void init() {
        this.underTest = new UserSessionMapper(new ModelMapper());
    }

    @Test
    public void testMapUserSessionDtoToEntity() {
        // Given
        String userName = "john";
        UserSession expected = new UserSession(userName);
        UserSessionDto userSessionDto = new UserSessionDto(userName);

        // When
        UserSession actual = this.underTest.mapUserSessionDtoToEntity(userSessionDto);

        // Then
        assertEquals(expected, actual);
    }
}