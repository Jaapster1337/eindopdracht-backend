package com.example.eindopdrachtbackend.dto.mapper;

import com.example.eindopdrachtbackend.dto.input.GameInputDto;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.Genre;
import com.example.eindopdrachtbackend.repository.GameRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameMapper {
    private static GameRepository gameRepository;

    public GameMapper(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

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
        if (game.getPublisher() != null){
            gameOutputDto.setPublisherId(game.getPublisher().getId());
        }
        List<Long> genreIds = new ArrayList<>();
        for(Genre genre: game.getGenre()){
            genreIds.add(genre.getId());
        }
        gameOutputDto.setGenreId(genreIds);
        gameOutputDto.setLikes(game.getLikes());
        gameOutputDto.setListOfComments(CommentMapper.fromListToOutputDtoList(game.getListOfComments()));
        gameOutputDto.setAmountOfFavorites(gameRepository.countUsersWhoFavoritedGame(game.getId()));
        return gameOutputDto;
    }

    public static List<GameOutputDto> fromListToOutputDtoList(List<Game> games){
        List<GameOutputDto> gameOutputDtos = new ArrayList<>();
        for (Game g : games){
            gameOutputDtos.add(fromModelToOutputDto(g));
        }
        return gameOutputDtos;

    }
}
