package com.example.eindopdrachtbackend.dto.mapper;

import com.example.eindopdrachtbackend.dto.input.GameInputDto;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.model.Game;

public class GameMapper {
    public static Game fromInputDtoToModel(GameInputDto gameInputDto){
        Game g = new Game();
        g.setName(gameInputDto.getName());
        g.setPublisher(gameInputDto.getPublisher());
        g.setGenre(gameInputDto.getGenre());
        g.setLikes(gameInputDto.getLikes());
        g.setListOfComments(gameInputDto.getListOfComments());
        g.setListOfFavorites(gameInputDto.getListOfFavorites());
        return g;
    }

    public static GameOutputDto fromModelToOutputDto(Game game){
        GameOutputDto gameOutputDto = new GameOutputDto();
        gameOutputDto.setId(game.getId());
        gameOutputDto.setName(game.getName());
        gameOutputDto.setPublisher(game.getPublisher());
        gameOutputDto.setGenre(game.getGenre());
        gameOutputDto.setLikes(game.getLikes());
        gameOutputDto.setListOfComments(game.getListOfComments());
        gameOutputDto.setListOfFavorites(game.getListOfFavorites());
        return gameOutputDto;
    }
}
