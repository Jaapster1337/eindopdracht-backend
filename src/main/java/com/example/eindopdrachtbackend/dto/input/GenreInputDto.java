package com.example.eindopdrachtbackend.dto.input;

import com.example.eindopdrachtbackend.model.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenreInputDto {

    private String name;
    private String description;
    private List<Game> listOfGames;
}
