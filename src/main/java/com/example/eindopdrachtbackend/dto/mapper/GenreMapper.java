package com.example.eindopdrachtbackend.dto.mapper;

import com.example.eindopdrachtbackend.dto.input.GenreInputDto;
import com.example.eindopdrachtbackend.dto.output.GenreOutputDto;
import com.example.eindopdrachtbackend.model.Genre;

public class GenreMapper {

    public static Genre fromInputDtoToModel(GenreInputDto genreInputDto){
        Genre g = new Genre();
        g.setName(genreInputDto.getName());
        g.setDescription(genreInputDto.getDescription());
        g.setListOfGames(genreInputDto.getListOfGames());
        return g;
    }

    public static GenreOutputDto fromModelToOutputDto(Genre genre){
        GenreOutputDto genreOutputDto = new GenreOutputDto();
        genreOutputDto.setId(genre.getId());
        genreOutputDto.setName(genre.getName());
        genreOutputDto.setDescription(genre.getDescription());
        genreOutputDto.setListOfGames(genre.getListOfGames());
        return genreOutputDto;
    }
}
