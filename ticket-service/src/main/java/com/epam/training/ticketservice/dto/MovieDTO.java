package com.epam.training.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private String title;
    private String genre;
    private Long length;

    @Override
    public String toString() {
        return String.format("%s (%s, %d minutes)", this.title, this.genre, this.length);
    }
}
