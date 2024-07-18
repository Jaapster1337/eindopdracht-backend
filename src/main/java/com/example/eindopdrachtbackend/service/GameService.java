package com.example.eindopdrachtbackend.service;

import com.example.eindopdrachtbackend.dto.input.GameInputDto;
import com.example.eindopdrachtbackend.dto.mapper.GameMapper;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.Comment;
import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.Publisher;
import com.example.eindopdrachtbackend.repository.CommentRepository;
import com.example.eindopdrachtbackend.repository.GameRepository;
import com.example.eindopdrachtbackend.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PublisherRepository publisherRepository;
    private final CommentRepository commentRepository;

    public GameService(GameRepository gameRepository, PublisherRepository publisherRepository, CommentRepository commentRepository) {
        this.gameRepository = gameRepository;
        this.publisherRepository = publisherRepository;
        this.commentRepository = commentRepository;
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

    public String assignPublisherToGame(Long gameId, Long publisherId){
        Optional<Game> g = gameRepository.findById(gameId);
        Optional<Publisher> p = publisherRepository.findById(publisherId);
        if(g.isPresent() && p.isPresent()){
            Game game = g.get();
            Publisher publisher = p.get();
            game.setPublisher(publisher);
            gameRepository.save(game);
            return "Publisher "+ publisherId +" has been added to game "+gameId;
        } else {
            throw new RecordNotFoundException("No game or publisher with that id has been found");
        }
    }

    public String assignCommentToGame(Long gameId, Long commentId){
        Optional<Game> g = gameRepository.findById(gameId);
        Optional<Comment> c = commentRepository.findById(commentId);
        if (g.isPresent() && c.isPresent()){
            Game game = g.get();
            Comment comment = c.get();
            List<Comment> commentList = game.getListOfComments();
            commentList.add(comment);
            game.setListOfComments(commentList);
            gameRepository.save(game);
            return "Comment "+commentId+" has been added to game "+gameId;
        } else {
            throw new RecordNotFoundException("No game or comment with that id has been found");
        }
    }
}


