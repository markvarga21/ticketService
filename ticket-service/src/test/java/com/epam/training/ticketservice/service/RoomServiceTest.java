package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.RoomDto;
import com.epam.training.ticketservice.entity.Room;
import com.epam.training.ticketservice.mapping.RoomMapper;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
    @InjectMocks
    private RoomService underTest;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomMapper roomMapper;

    @Test
    public void testSaveRoom() {
        // Given
        RoomDto room = new RoomDto();
        room.setName("room");
        room.setChairRowsCount(5L);
        room.setChairColumnsCount(5L);
        String expected = String.format("Room '%s' created!", room.getName());

        // When
        String actual = this.underTest.saveRoom(room);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRooms() {
        // Given
        Room room1 = new Room("room1", 5L, 5L);
        Room room2 = new Room("room2", 11L, 11L);
        List<Room> rooms = List.of(room1, room2);
        List<RoomDto> expected = Stream.of(room1, room2)
                                .map(this.roomMapper::mapRoomToDto)
                                .collect(Collectors.toList());

        // When
        when(this.roomRepository.findAll())
                .thenReturn(rooms);
        List<RoomDto> actual = this.underTest.getRooms();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRoomsWhenEmpty() {
        // Given

        // When
        when(this.roomRepository.findAll())
                .thenReturn(List.of());
        List<RoomDto> actual = this.underTest.getRooms();

        // Then
        assertTrue(actual.isEmpty());
    }

    @Test
    public void testDeleteRoomShouldReturnZeroWhenRoomNotExists() {
        // Given
        String notExistingRoomName = "something";
        int expectedReturnValue = 0;

        // When
        when(this.roomRepository.deleteByName(notExistingRoomName))
                .thenReturn(0);
        int actualReturnValue = this.underTest.deleteRoom(notExistingRoomName);

        // Then
        assertEquals(expectedReturnValue, actualReturnValue);
    }

    @Test
    public void testDeleteRoomShouldReturnOneWhenRoomExists() {
        // Given
        Room room = new Room("room", 5L, 5L);
        int expectedReturnValue = 1;

        // When
        when(this.roomRepository.deleteByName(room.getName()))
                .thenReturn(1);
        int actualReturnValue = this.underTest.deleteRoom(room.getName());

        // Then
        assertEquals(expectedReturnValue, actualReturnValue);
    }

    @Test
    public void testUpdateRoomWhenRoomNotExists() {
        // Given
        RoomDto roomDto = new RoomDto();
        roomDto.setName("dummy");
        String expected = String.format("Cannot update, because room '%s' not found!", roomDto.getName());

        // When
        when(this.roomRepository.findById(roomDto.getName()))
                .thenReturn(Optional.empty());
        String actual = this.underTest.updateRoom(roomDto);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateRoomWhenRoomExists() {
        // Given
        RoomDto roomDto = new RoomDto("dummy", 5L, 5L);
        String expected = String.format("Room '%s' updated successfully!", roomDto.getName());
        Room room = new Room("dummy", 5L, 5L);

        // When
        when(this.roomRepository.findById(roomDto.getName()))
                .thenReturn(Optional.of(room));
        String actual = this.underTest.updateRoom(roomDto);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testRoomExistsShouldReturnFalseWhenNotExists() {
        // Given
        boolean expected = false;

        // When
        when(this.roomRepository.existsById(anyString()))
                .thenReturn(false);
        boolean actual = this.underTest.roomExists(anyString());

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testRoomExistsShouldReturnTrueWhenExists() {
        // Given
        boolean expected = true;

        // When
        when(this.roomRepository.existsById(anyString()))
                .thenReturn(true);
        boolean actual = this.underTest.roomExists(anyString());

        // Then
        assertEquals(expected, actual);
    }
}