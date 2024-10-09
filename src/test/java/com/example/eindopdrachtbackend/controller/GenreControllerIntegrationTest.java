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
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    private Game game;

    @BeforeEach
    void setUp() {
        genre = new Genre();
        genre.setName("moba2");
        genre.setDescription("moba game");
        genre = genreRepository.save(genre);

        game = new Game();
        game.setName("game of life");
        game.setId(1L);
        List<Genre> genreList = new ArrayList<>();
        genreList.add(genre);
        game.setGenre(genreList);
        game = gameRepository.save(game);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game);
        genre = genreRepository.save(genre);

        genreInputDto = new GenreInputDto();
        genreInputDto.setName("simulation");
        genreInputDto.setDescription("Hallo ik ben een genre");
        genreInputDto.setListOfGames(gameList);

        genre = new Genre();
        genre.setName("moba");
        genre.setDescription("moba game");


        List<Genre> genres = new ArrayList<>();
        genres.add(genre);


        List<Game> games = new ArrayList<>();
        games.add(game);
        genre.setListOfGames(games);

        genre = genreRepository.save(genre);
        gameRepository.save(game);

        genreInputDto = new GenreInputDto();
        genreInputDto.setName("simulation");
        genreInputDto.setDescription("Hallo ik ben een genre");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithMockUser(username = "john_doe", authorities = {"ROLE_ADMIN"})
    @Order(1)
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
    @WithMockUser(username = "john_doe", authorities = {"ROLE_ADMIN"})
    @Order(2)
    void testGetAllGenres() throws Exception {
        genreService.createGenre(genreInputDto);

        mockMvc.perform(get("/genres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("moba2"));
    }

    @Test
    @WithMockUser(username = "john_doe", authorities = {"ROLE_ADMIN"})
    @Order(3)
    void testGetGenreById() throws Exception {
        GenreOutputDto createdGenre = genreService.createGenre(genreInputDto);

        mockMvc.perform(get("/genres/{id}", createdGenre.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdGenre.getId()))
                .andExpect(jsonPath("$.name").value("simulation"));
    }

    @Test
    @WithMockUser(username = "john_doe", authorities = {"ROLE_ADMIN"})
    @Order(4)
    void testUpdateGenre() throws Exception {
        GenreOutputDto createdGenre = genreService.createGenre(genreInputDto);

        Genre newGenre = new Genre();
        newGenre.setName("BANAAN");
        newGenre.setDescription("KAAS");


        newGenre = genreRepository.save(newGenre);


        GenreInputDto updatedGenreDto = new GenreInputDto();
        updatedGenreDto.setName("Updated Genre");
        updatedGenreDto.setDescription("IK BEN EEN TEST");
        updatedGenreDto.setListOfGames(List.of(game));

        String updatedGenreJson = objectMapper.writeValueAsString(updatedGenreDto);

        mockMvc.perform(put("/genres/{id}", createdGenre.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedGenreJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdGenre.getId()))
                .andExpect(jsonPath("$.name").value("Updated Genre"));
    }

    @Test
    @WithMockUser(username = "john_doe", authorities = {"ROLE_ADMIN"})
    @Order(5)
    void testDeleteGenre() throws Exception {
        GenreOutputDto createdGenre = genreService.createGenre(genreInputDto);

        mockMvc.perform(delete("/genres/{id}", genre.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Genre with id 15 has been removed"));
    }

    @Test
    @WithMockUser(username = "john_doe", authorities = {"ROLE_ADMIN"})
    @Order(6)
    void testGetGenreById_NotFound() throws Exception {
        mockMvc.perform(get("/genres/{id}", 9999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("No genre with this id was found"));
    }
}
