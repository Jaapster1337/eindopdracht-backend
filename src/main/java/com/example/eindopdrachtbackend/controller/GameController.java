package com.example.eindopdrachtbackend.controller;

import com.example.eindopdrachtbackend.dto.input.GameInputDto;
import com.example.eindopdrachtbackend.dto.input.IdInputDto;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.Image;
import com.example.eindopdrachtbackend.service.GameService;
import com.example.eindopdrachtbackend.service.ImageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;
    private final ImageService imageService;

    public GameController(GameService gameService, ImageService imageService) {
        this.gameService = gameService;
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<GameOutputDto> createGame(@Valid @RequestBody GameInputDto gameInputDto){
        GameOutputDto gameOutputDto = gameService.createGame(gameInputDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(gameOutputDto.getId()).toUri();
        return ResponseEntity.created(uri).body(gameOutputDto);
    }

    @GetMapping
    public ResponseEntity<List<GameOutputDto>> returnAllGames(){
        return ResponseEntity.ok().body(gameService.getAllGames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameOutputDto> getGameById(@PathVariable int id) throws RecordNotFoundException{
        return ResponseEntity.ok().body(gameService.getGameById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameOutputDto> updateGame(@PathVariable int id, @RequestBody GameInputDto game){
        return ResponseEntity.ok().body(gameService.updateGame(id, game));
    }

    @PutMapping("likes/{id}")
    public ResponseEntity<String> updateLikes(@PathVariable int id){
        return ResponseEntity.ok().body(gameService.updateLikes(id));
    }

    @PutMapping("/{gameId}/publisher")
    public ResponseEntity<Void> assignPublisherToGame(@PathVariable Long gameId, @RequestBody IdInputDto publisherId){
        Long longPublisherId = publisherId.id;
        gameService.assignPublisherToGame(gameId, longPublisherId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{gameId}/comment")
    public ResponseEntity<Void> assignCommentToGame(@PathVariable Long gameId, @RequestBody IdInputDto commentId){
        Long longCommentId = commentId.id;
        gameService.assignCommentToGame(gameId, longCommentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{gameId}/genre")
    public ResponseEntity<Void> assignGenreToGame(@PathVariable Long gameId, @RequestBody IdInputDto genreId){
        Long longGenreId = genreId.id;
        gameService.assingGenreToGame(gameId, longGenreId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{gameId}/image")
    public ResponseEntity<GameOutputDto> addImageToGame(@PathVariable Long gameId, @RequestBody MultipartFile file) throws IOException {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/games/")
                .path(Objects.requireNonNull(gameId.toString()))
                .path("/image")
                .toUriString();

        Image image = imageService.storeFile(file, url);
        GameOutputDto game = gameService.addImageToGame(gameId, image);
        return ResponseEntity.created(URI.create(url)).body(game);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable int id){
        return ResponseEntity.ok().body(gameService.deleteGame(id));
    }
}
