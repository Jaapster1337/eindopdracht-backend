package com.example.eindopdrachtbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NotBlank
    private String content;
    private LocalDate postDate;
    @PositiveOrZero
    private Long amountOfLikes;
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
}
