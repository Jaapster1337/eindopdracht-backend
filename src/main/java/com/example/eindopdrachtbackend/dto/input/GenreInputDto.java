package com.example.eindopdrachtbackend.dto.input;

import com.example.eindopdrachtbackend.model.Game;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenreInputDto {

    @NotEmpty
    private String name;
    @NotEmpty
    @Size(max = 100)
    private String description;
    private List<Game> listOfGames;
}
