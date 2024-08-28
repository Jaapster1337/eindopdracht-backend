package com.example.eindopdrachtbackend.model;

import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(unique = true)
    private String name;
    private LocalDate creationDate;
    private String description;
    @OneToMany(mappedBy = "publisher", orphanRemoval = true)
    private List<Game> listOfGame;
}
