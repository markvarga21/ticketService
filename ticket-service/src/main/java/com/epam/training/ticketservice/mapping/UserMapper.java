package com.epam.training.ticketservice.mapping;

import com.epam.training.ticketservice.dto.UserDto;
import com.epam.training.ticketservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserDto mapUserToDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }

    public User mapUserDtoToEntity(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }
}
