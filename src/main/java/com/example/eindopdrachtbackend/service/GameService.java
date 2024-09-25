package com.example.eindopdrachtbackend.service;

import com.example.eindopdrachtbackend.dto.input.GameInputDto;
import com.example.eindopdrachtbackend.dto.mapper.GameMapper;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.*;
import com.example.eindopdrachtbackend.repository.CommentRepository;
import com.example.eindopdrachtbackend.repository.GameRepository;
import com.example.eindopdrachtbackend.repository.GenreRepository;
import com.example.eindopdrachtbackend.repository.PublisherRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PublisherRepository publisherRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;

    public GameService(GameRepository gameRepository, PublisherRepository publisherRepository, CommentRepository commentRepository, GenreRepository genreRepository) {
        this.gameRepository = gameRepository;
        this.publisherRepository = publisherRepository;
        this.commentRepository = commentRepository;
        this.genreRepository = genreRepository;
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

    public long getGameCount(){
        return gameRepository.count();
    }

    public GameOutputDto createGame(@Valid GameInputDto gameInputDto){
        Optional<Publisher> p = publisherRepository.findById(gameInputDto.getPublisherId());

        Game game = GameMapper.fromInputDtoToModel(gameInputDto);
        p.ifPresent(game::setPublisher);

        List<Genre> genres = new ArrayList<>();
        for(Long genreId: gameInputDto.getGenreId()){
            Optional<Genre> genreOpt = genreRepository.findById(genreId);
            genreOpt.ifPresent(genres::add);
        }
        game.setGenre(genres);
        game.setLikes(0L);
        Game g = gameRepository.save(game);
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

    public GameOutputDto updateGame(@Valid long id, GameInputDto gameInputDto){
        Optional<Game> g = gameRepository.findById(id);
        if(g.isPresent()){
            Game game = g.get();
            game.setName(gameInputDto.getName());
            Optional<Publisher> publisher = publisherRepository.findById(gameInputDto.getPublisherId());
            if (publisher.isPresent()) {
                game.setPublisher(publisher.get());
            } else{
                game.setPublisher(g.get().getPublisher());
            }
            if(!gameInputDto.getGenreId().isEmpty()){
                List<Genre> genreList = new ArrayList<>();
                List<Long> genres = gameInputDto.getGenreId();
                for(Long genreId: genres){
                    Optional<Genre> specificGenre = genreRepository.findById(genreId);
                    if (specificGenre.isPresent()){
                        genreList.add(specificGenre.get());
                    } else {
                        throw new RecordNotFoundException("No genre was found");
                    }
                }
                game.setGenre(genreList);
            }

            game.setLikes(gameInputDto.getLikes());
            game.setListOfComments(gameInputDto.getListOfComments());
            game.setListOfFavorites(gameInputDto.getListOfFavorites());
            gameRepository.save(game);
            return GameMapper.fromModelToOutputDto(game);
        } else {
            throw new RecordNotFoundException("No game with id " + id +" found");
        }
    }

    public String updateLikes(long id){
        Optional<Game> g = gameRepository.findById(id);
        if (g.isPresent()){
            Game game = g.get();
            Long likes = game.getLikes();
            likes++;
            game.setLikes(likes);
            gameRepository.save(game);
            return "Game with id "+id+" now has "+ game.getLikes() + " likes";
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

    public String assignPublisherToGame(@Valid Long gameId, Long publisherId){
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

    public String assingGenreToGame(Long gameId, Long genreId){
        Optional<Game> ga = gameRepository.findById(gameId);
        Optional<Genre> ge = genreRepository.findById(genreId);
        if (ga.isPresent() && ge.isPresent()){
            Game game = ga.get();
            Genre genre = ge.get();
            List<Genre> genreList = game.getGenre();
            genreList.add(genre);
            game.setGenre(genreList);
            gameRepository.save(game);
            return "Comment "+genreId+" has been added to game "+gameId;
        }else{
            throw new RecordNotFoundException("No game or genre with that id has been found");
        }
    }
    @Transactional
    public GameOutputDto addImageToGame(@Valid long gameId, Image image){
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if(optionalGame.isEmpty()){
            throw new RecordNotFoundException("Game with id "+gameId+" not found");
        }
        Game game = optionalGame.get();
        game.setGameCover(image);
        gameRepository.save(game);
        return GameMapper.fromModelToOutputDto(game);
    }
}


