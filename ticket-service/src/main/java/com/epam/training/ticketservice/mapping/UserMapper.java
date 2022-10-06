package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.UserDTO;
import com.epam.training.ticketservice.dto.UserSessionDTO;
import com.epam.training.ticketservice.entity.User;
import com.epam.training.ticketservice.entity.UserSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserDTO mapUserToDto(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }

    public User mapUserDtoToEntity(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }
}
