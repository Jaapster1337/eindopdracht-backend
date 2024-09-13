package com.example.eindopdrachtbackend.dto.input;

import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.Image;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PublisherInputDto {
    @NotEmpty
    private String name;
    @Past
    private LocalDate creationDate;
    @NotEmpty
    private String description;
    private List<Game> listOfGame;
    private Image publisherLogo;
}