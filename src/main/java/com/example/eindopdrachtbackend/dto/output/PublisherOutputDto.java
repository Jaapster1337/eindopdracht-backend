package com.example.eindopdrachtbackend.dto.output;

import com.example.eindopdrachtbackend.model.Game;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PublisherOutputDto {

    private long id;
    private String name;
    private LocalDate creationDate;
    private String description;
    private List<Game> listOfGame;}
