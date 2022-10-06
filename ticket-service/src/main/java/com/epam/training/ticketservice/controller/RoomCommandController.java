package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.dto.MovieDTO;
import com.epam.training.ticketservice.dto.RoomDTO;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.SigningService;
import com.epam.training.ticketservice.util.Formatter;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class RoomCommandController {
    private final RoomService roomService;
    private final SigningService signingService;
    private final Formatter formatter;

    public Availability checkAdmin() {
        boolean isAdminLoggedIn = this.signingService.isAdminLoggedIn();
        return isAdminLoggedIn ? Availability.available() : Availability.unavailable("Only admin has access for this command!");
    }
    public Availability anyone() {
        return Availability.available();
    }


    @ShellMethod(value = "Creating rooms.", key = "create room")
    @ShellMethodAvailability("checkAdmin")
    public String createRoom(String name, Long chairRowCount, Long chairColumnCount) {
        RoomDTO roomToSave = new RoomDTO(name, chairRowCount, chairColumnCount);
        return this.roomService.saveRoom(roomToSave);
    }

    @ShellMethod(value = "Listing rooms.", key = "list rooms")
    @ShellMethodAvailability("anyone")
    public String listRooms() {
        var rooms = this.roomService.getRooms();
        return this.formatter.formatRoomList(rooms);
    }

    @ShellMethod(value = "Deleting rooms.", key = "delete room")
    @ShellMethodAvailability("checkAdmin")
    public String deleteRoom(String name) {
        if (this.roomService.deleteRoom(name) == 1) {
            return String.format("Room '%s' deleted successfully!", name);
        }
        return String.format("Something went wrong when deleting room '%s'", name);
    }

    @ShellMethod(value = "Updating rooms.", key = "update room")
    @ShellMethodAvailability("checkAdmin")
    public String updateRoom(String name, Long chairRowsCount, Long chairColumnsCount) {
        RoomDTO roomToUpdate = new RoomDTO(name, chairRowsCount, chairColumnsCount);
        return this.roomService.updateRoom(roomToUpdate);
    }
}
