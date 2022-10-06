package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.UserSessionDTO;
import com.epam.training.ticketservice.entity.UserSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSessionMapper {
    private final ModelMapper modelMapper;

    public UserSessionDTO mapUserSessionToDto(UserSession userSession) {
        return this.modelMapper.map(userSession, UserSessionDTO.class);
    }

    public UserSession mapUserSessionDtoToEntity(UserSessionDTO userSessionDTO) {
        return this.modelMapper.map(userSessionDTO, UserSession.class);
    }
}
