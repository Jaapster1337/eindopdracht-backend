package com.example.eindopdrachtbackend.service;

import com.example.eindopdrachtbackend.dto.input.GenreInputDto;
import com.example.eindopdrachtbackend.dto.mapper.GenreMapper;
import com.example.eindopdrachtbackend.dto.output.GenreOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.Genre;
import com.example.eindopdrachtbackend.repository.GenreRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreOutputDto> getAllGenres() {
        Genre g = new Genre();
        List<Genre> allGenres = genreRepository.findAll();
        List<GenreOutputDto> allGenresOutputList = new ArrayList<>();
        for (Genre genre : allGenres) {
            allGenresOutputList.add(GenreMapper.fromModelToOutputDto(genre));
        }
        return allGenresOutputList;
    }

    public GenreOutputDto createGenre(@Valid GenreInputDto genreInputDto) {
        Genre g = genreRepository.save(GenreMapper.fromInputDtoToModel(genreInputDto));
        return GenreMapper.fromModelToOutputDto(g);
    }

    public GenreOutputDto getGenreById(long id) {
        Optional<Genre> g = genreRepository.findById(id);
        if (g.isPresent()) {
            return GenreMapper.fromModelToOutputDto(g.get());
        } else {
            throw new RecordNotFoundException("No genre with this id was found");
        }
    }

    public GenreOutputDto updateGenre(@Valid long id, GenreInputDto genreInputDto) {
        Optional<Genre> g = genreRepository.findById(id);
        if (g.isPresent()) {
            Genre genre = g.get();
            genre.setName(genreInputDto.getName());
            genre.setDescription(genreInputDto.getDescription());
            genre.setListOfGames(genreInputDto.getListOfGames());
            genreRepository.save(genre);
            return GenreMapper.fromModelToOutputDto(genre);
        } else {
            throw new RecordNotFoundException("No genres with id " + id + " found");
        }
    }

    public String deleteGenre(long id) {
        Optional<Genre> g = genreRepository.findById(id);

        if (g.isPresent()) {
            Genre genre = g.get();
            List<Game> games = genre.getListOfGames();
            for(Game game : games){
                game.getGenre().remove(genre);
            }
            genreRepository.delete(g.get());
            return "Genre with id " + id + " has been removed";
        } else {
            throw new RecordNotFoundException("No genres with id " + id + " found");
        }
    }
}
