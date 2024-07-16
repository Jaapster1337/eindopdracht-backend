package com.example.eindopdrachtbackend.dto.input;

import com.example.eindopdrachtbackend.model.Game;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PublisherInputDto {

    private String name;
    private LocalDate creationDate;
    private String description;
    private List<Game> listOfGame;
}
