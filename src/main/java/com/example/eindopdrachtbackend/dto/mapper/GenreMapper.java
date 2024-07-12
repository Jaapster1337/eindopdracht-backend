package com.example.eindopdrachtbackend.dto.mapper;

import com.example.eindopdrachtbackend.dto.input.GenreInputDto;
import com.example.eindopdrachtbackend.dto.output.GenreOutputDto;
import com.example.eindopdrachtbackend.model.Genre;

public class GenreMapper {

    public static Genre fromInputDtoToModel(GenreInputDto genreInputDto){
        Genre g = new Genre();
        g.setName(g.getName());
        g.setDescription(g.getDescription());
        g.setListOfGames(g.getListOfGames());
        return g;
    }

    public static GenreOutputDto fromModelToOutputDto(Genre genre){
        GenreOutputDto genreOutputDto = new GenreOutputDto();
        genreOutputDto.setId(genre.getId());
        genreOutputDto.setDescription(genre.getDescription());
        genreOutputDto.setListOfGames(genreOutputDto.getListOfGames());
        return genreOutputDto;
    }
}
