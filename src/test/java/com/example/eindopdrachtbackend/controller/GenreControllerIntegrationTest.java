package com.example.eindopdrachtbackend.controller;

import com.example.eindopdrachtbackend.dto.input.GenreInputDto;
import com.example.eindopdrachtbackend.dto.output.GenreOutputDto;
import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.Genre;
import com.example.eindopdrachtbackend.repository.GameRepository;
import com.example.eindopdrachtbackend.repository.GenreRepository;
import com.example.eindopdrachtbackend.service.GenreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class GenreControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GenreService genreService;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private GameRepository gameRepository;

    private GenreInputDto genreInputDto;

    private Genre genre;

    @BeforeEach
    void setUp() {
//        genre = new Genre();
////        genre.setId(123L);
//        genre.setName("moba");
//        genre.setDescription("moba game");
//        genre  = genreRepository.save(genre);
////        genreRepository.flush();
//        Game game = new Game();
//        game.setName("game of life");
//        game.setId(10L);
//        List<Genre> genrelist = new ArrayList<>();
//        genrelist.add(genre);
//        game.setGenre(genrelist);
//        game = gameRepository.save(game);
////        gameRepository.flush();
////        genre.setListOfGames(List.of(game));
//        List<Game> gamelist = new ArrayList<>();
//        gamelist.add(game);
//        genre = genreRepository.save(genre);
//        genreInputDto = new GenreInputDto();
//        genreInputDto.setName("simulation");
//        genreInputDto.setDescription("Hallo ik ben een genre");
//
//        genreInputDto.setListOfGames(gamelist);

        // Create a genre
        genre = new Genre();
        genre.setName("moba");
        genre.setDescription("moba game");

        // Create a game and associate it with the genre
        Game game = new Game();
        game.setName("game of life");

        // Set the genre for the game
        List<Genre> genres = new ArrayList<>();
        genres.add(genre);
        game.setGenre(genres);

        // Add the game to the genre's list of games
        List<Game> games = new ArrayList<>();
        games.add(game);
        genre.setListOfGames(games);

        // Save both entities
        genre = genreRepository.save(genre);  // Saves the genre and cascades the game if configured
        gameRepository.save(game);            // Alternatively, if cascading is not set

        // Create the GenreInputDto for test cases
        genreInputDto = new GenreInputDto();
        genreInputDto.setName("simulation");
        genreInputDto.setDescription("Hallo ik ben een genre");
        genreInputDto.setListOfGames(games);

    }
    @AfterEach
    void tearDown(){
    }

    @Test
    void testCreateGenre() throws Exception {
        String genreInputDtoJson = objectMapper.writeValueAsString(genreInputDto);

        MvcResult result = mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(genreInputDtoJson))
                .andExpect(status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        GenreOutputDto genreOutputDto = objectMapper.readValue(response, GenreOutputDto.class);

        assertThat(genreOutputDto.getId()).isNotNull();
        assertThat(genreOutputDto.getName()).isEqualTo("simulation");
    }

    @Test
    void testGetAllGenres() throws Exception {
        // First create a genre
        genreService.createGenre(genreInputDto);

        mockMvc.perform(get("/genres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("moba"));
    }

    @Test
    void testGetGenreById() throws Exception {
        // First create a genre and get its ID
        GenreOutputDto createdGenre = genreService.createGenre(genreInputDto);

        mockMvc.perform(get("/genres/{id}", createdGenre.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdGenre.getId()))
                .andExpect(jsonPath("$.name").value("simulation"));
    }

    @Test
    void testUpdateGenre() throws Exception {
        GenreOutputDto createdGenre = genreService.createGenre(genreInputDto);
        Game game2 = new Game();
        Genre genre2 = new Genre();

        GenreInputDto updatedGenre = new GenreInputDto();
        game2.setGenre(List.of(genre2));
        updatedGenre.setName("Updated Genre");
        updatedGenre.setListOfGames(List.of(game2));

        String updatedGenreJson = objectMapper.writeValueAsString(updatedGenre);

        mockMvc.perform(put("/genres/{id}", createdGenre.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedGenreJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdGenre.getId()))
                .andExpect(jsonPath("$.name").value("Updated Genre"));
    }

    @Test
    void testDeleteGenre() throws Exception {
        // First create a genre and get its ID
        GenreOutputDto createdGenre = genreService.createGenre(genreInputDto);

        mockMvc.perform(delete("/genres/{id}", genre.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Genre with id 1 has been removed"));
    }

    @Test
    void testGetGenreById_NotFound() throws Exception {
        // Try to get a genre that doesn't exist
        mockMvc.perform(get("/genres/{id}", 9999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("No genre with this id was found"));
    }
}
