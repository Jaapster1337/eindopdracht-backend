package com.example.eindopdrachtbackend.dto.output;

import com.example.eindopdrachtbackend.model.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class GenreOutputDto {

    private Long id;
    private String name;
    private String description;
    private List<GameOutputDto> listOfGames;
}
