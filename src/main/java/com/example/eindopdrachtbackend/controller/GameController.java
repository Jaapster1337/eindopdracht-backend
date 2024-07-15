package com.example.eindopdrachtbackend.controller;

import com.example.eindopdrachtbackend.dto.input.GameInputDto;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.service.GameService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable int id){
        return ResponseEntity.ok().body(gameService.deleteGame(id));
    }
}
