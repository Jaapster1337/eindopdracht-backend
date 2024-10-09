package com.example.eindopdrachtbackend.model;

import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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
    @Past(message = "Creation date must be in the past")
    private LocalDate creationDate;
    @Size(max = 200)
    private String description;
    @OneToMany(mappedBy = "publisher", orphanRemoval = true)
    private List<Game> listOfGame;
    @OneToOne
    private Image publisherLogo;
}
