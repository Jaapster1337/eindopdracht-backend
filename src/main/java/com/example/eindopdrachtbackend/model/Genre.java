package com.example.eindopdrachtbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(unique = true)
    private String name;
    @NotEmpty
    @Size(max = 100)
    private String description;
    @ManyToMany(mappedBy = "genre")
    private List<Game> listOfGames;
}
