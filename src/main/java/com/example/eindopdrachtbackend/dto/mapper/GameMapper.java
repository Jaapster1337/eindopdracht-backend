package com.example.eindopdrachtbackend.dto.mapper;

import com.example.eindopdrachtbackend.dto.input.GameInputDto;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class GameMapper {
    public static Game fromInputDtoToModel(GameInputDto gameInputDto){
        Game g = new Game();
        g.setName(gameInputDto.getName());
        g.setLikes(gameInputDto.getLikes());
        g.setListOfComments(gameInputDto.getListOfComments());
        g.setListOfFavorites(gameInputDto.getListOfFavorites());
        return g;
    }

    public static GameOutputDto fromModelToOutputDto(Game game){
        GameOutputDto gameOutputDto = new GameOutputDto();
        gameOutputDto.setId(game.getId());
        gameOutputDto.setName(game.getName());
        gameOutputDto.setPublisherId(game.getPublisher().getId());
        List<Long> genreIds = new ArrayList<>();
        for(Genre genre: game.getGenre()){
            genreIds.add(genre.getId());
        }
        gameOutputDto.setGenreId(genreIds);
        gameOutputDto.setLikes(game.getLikes());
        gameOutputDto.setListOfComments(game.getListOfComments());
        gameOutputDto.setListOfFavorites(game.getListOfFavorites());
        return gameOutputDto;
    }
}
