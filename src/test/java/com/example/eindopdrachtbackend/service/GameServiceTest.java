package com.example.eindopdrachtbackend.service;

import com.example.eindopdrachtbackend.dto.input.GameInputDto;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.Genre;
import com.example.eindopdrachtbackend.model.Publisher;
import com.example.eindopdrachtbackend.repository.GameRepository;
import com.example.eindopdrachtbackend.repository.GenreRepository;
import com.example.eindopdrachtbackend.repository.PublisherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    GameRepository gameRepository;
    @Mock
    GenreRepository genreRepository;

    @Mock
    PublisherRepository publisherRepository;

    @InjectMocks
    GameService gameService;

    private List<Genre> genres = new ArrayList<>();
    private GameInputDto gameInputDto;
    private Game game1;
    private Game game2;

    private Publisher publisher1;

    @BeforeEach
    void setUp() {
        // genre setup
        Genre genre = new Genre();
        genre.setName("Simulation");
        genre.setId(1l);

        Genre genre2 = new Genre();
        genre.setName("Deck builders");
        genre.setId(2l);

        genres.add(genre);
        genres.add(genre2);

        //publisher setup
        publisher1 = new Publisher();
        publisher1.setId(1l);
        publisher1.setName("Henk");
        publisher1.setCreationDate(LocalDate.parse("2001-09-11"));

        // game input
        gameInputDto = new GameInputDto();
        gameInputDto.setName("tetris");
        gameInputDto.setPublisherId(1L);
        gameInputDto.setGenreId(Collections.singletonList(1l));
        gameInputDto.setLikes(5L);

        game1 = new Game();
        game1.setGenre(List.of(genre));
        game1.setName("tetris");
        game1.setLikes(0l);

        game2 = new Game();
        game2.setGenre(List.of(genre));
        game2.setName("tetris");
        game2.setLikes(0l);


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("should return all games")
    void getAllGames() {
        //arrange
        when(gameRepository.findAll()).thenReturn(List.of(game1, game2));
        //act
        List<GameOutputDto> gamesFound =  gameService.getAllGames();

        //assert
        assertEquals(game1.getName(), gamesFound.get(0).getName());
        assertEquals(game2.getName(), gamesFound.get(1).getName());

    }

    @Test
    @DisplayName("should add game")
    void createGame() {
        //arrange
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(genres.get(0)));
        when(gameRepository.save(any())).thenReturn(game1);
        when(publisherRepository.findById(any())).thenReturn(Optional.of(publisher1));
        //ACT
        GameOutputDto gameOutputDto =  gameService.createGame(gameInputDto);


        //assert
        assertEquals("tetris", gameOutputDto.getName());
        assertEquals("Henk", publisher1.getName());

    }

    @Test
    @DisplayName("Should return game by ID")
    void getGameById() {
        //arrange
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game1));

        //act
        GameOutputDto gameOutputDto = gameService.getGameById(1L);

        //assert
        assertEquals(game1.getName(), gameOutputDto.getName());
    }

    @Test
    void updateGame() {
        //arrange
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game1));
        //act
        GameOutputDto gameOutputDto = gameService.getGameById(1L);
        gameOutputDto.setName("Call Of Duty");

        //assert
    }

    @Test
    void deleteGame() {
        //arrange
        //act
        //assert
    }

    @Test
    void assignPublisherToGame() {
        //arrange
        //act
        //assert
    }

    @Test
    void assignCommentToGame() {
        //arrange
        //act
        //assert
    }

    @Test
    void assingGenreToGame() {
        //arrange
        //act
        //assert
    }
}