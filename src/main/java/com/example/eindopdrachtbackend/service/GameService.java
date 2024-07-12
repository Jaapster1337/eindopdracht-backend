package com.example.eindopdrachtbackend.service;

import com.example.eindopdrachtbackend.dto.input.GameInputDto;
import com.example.eindopdrachtbackend.dto.mapper.GameMapper;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameOutputDto> getAllGames(){
        Game g = new Game();
        List<Game> allGames = gameRepository.findAll();
        List<GameOutputDto> allGameOutputList = new ArrayList<>();
        for(Game game : allGames){
            allGameOutputList.add(GameMapper.fromModelToOutputDto(game));
        }
        return allGameOutputList;
    }

    public GameOutputDto createGame(GameInputDto gameInputDto){
        Game g = gameRepository.save(GameMapper.fromInputDtoToModel(gameInputDto));
        return GameMapper.fromModelToOutputDto(g);
    }

    public GameOutputDto getGameById(long id){
        Optional<Game> g = gameRepository.findById(id);
        if(g.isPresent()){
            return GameMapper.fromModelToOutputDto(g.get());
        } else {
            throw new RecordNotFoundException("No games with "+ id +" found");
        }
    }

    public GameOutputDto updateGame(long id, GameInputDto gameInputDto){
        Optional<Game> g = gameRepository.findById(id);
        if(g.isPresent()){
            g.get().setName(gameInputDto.getName());
            g.get().setPublisher(gameInputDto.getPublisher());
            g.get().setGenre(gameInputDto.getGenre());
            g.get().setLikes(gameInputDto.getLikes());
            g.get().setListOfComments(gameInputDto.getListOfComments());
            g.get().setListOfFavorites(gameInputDto.getListOfFavorites());
            gameRepository.save(g.get());
            return GameMapper.fromModelToOutputDto(g.get());
        } else {
            throw new RecordNotFoundException("No game with id " + id +" found");
        }
    }

    public String deleteGame(long id){
        Optional<Game> g = gameRepository.findById(id);
        if(g.isPresent()){
            gameRepository.delete(g.get());
            return "Game with id " +id+" has been removed";
        } else {
            throw new RecordNotFoundException("No games with "+ id +" found");
        }
    }
}


