package com.example.eindopdrachtbackend.service;

import com.example.eindopdrachtbackend.dto.input.GenreInputDto;
import com.example.eindopdrachtbackend.dto.mapper.GenreMapper;
import com.example.eindopdrachtbackend.dto.output.GenreOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.Genre;
import com.example.eindopdrachtbackend.repository.GenreRepository;
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

    public List<GenreOutputDto> getAllGenres(){
        Genre g = new Genre();
        List<Genre> allGenres = genreRepository.findAll();
        List<GenreOutputDto> allGenresOutputList = new ArrayList<>();
        for(Genre genre: allGenres){
            allGenresOutputList.add(GenreMapper.fromModelToOutputDto(genre));
        }
        return allGenresOutputList;
    }

    public GenreOutputDto createGenre(GenreInputDto genreInputDto) {
        Genre g = genreRepository.save(GenreMapper.fromInputDtoToModel(genreInputDto));
        return GenreMapper.fromModelToOutputDto(g);
    }

    public GenreOutputDto getGenreById(long id){
        Optional<Genre> g = genreRepository.findById(id);
        if(g.isPresent()){
            return GenreMapper.fromModelToOutputDto(g.get());
        } else {
            throw new RecordNotFoundException("No genre with this id was found");
        }
    }

    public GenreOutputDto updateGenre(long id, GenreInputDto genreInputDto){
        Optional<Genre> g = genreRepository.findById(id);
        if(g.isPresent()){
            g.get().setName(genreInputDto.getName());
            g.get().setDescription(genreInputDto.getDescription());
            g.get().setListOfGames(genreInputDto.getListOfGames());
            genreRepository.save(g.get());
            return GenreMapper.fromModelToOutputDto(g.get());
        }else {
            throw new RecordNotFoundException("No genres with id " + id + " found");
        }
    }

    public String deleteGenre(long id){
        Optional<Genre> g = genreRepository.findById(id){
            if(g.isPresent()){
                genreRepository.delete(g.get());
                return "Genre with id " +id+ " has been removed";
            } else {
                throw new RecordNotFoundException("No genres with id " + id + " found");
            }
        }
    }
}
