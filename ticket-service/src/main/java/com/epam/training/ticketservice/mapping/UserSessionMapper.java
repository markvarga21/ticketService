package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.UserSessionDto;
import com.epam.training.ticketservice.entity.UserSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSessionMapper {
    private final ModelMapper modelMapper;

    public UserSession mapUserSessionDtoToEntity(UserSessionDto userSessionDto) {
        return this.modelMapper.map(userSessionDto, UserSession.class);
    }
}
