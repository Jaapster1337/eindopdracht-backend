package com.example.eindopdrachtbackend.model;

import java.time.LocalDate;
import java.util.List;

public class Publisher {

    private Long id;
    private String name;
    private LocalDate creationDate;
    private String description;
    private List<Game> listOfGame;
}
