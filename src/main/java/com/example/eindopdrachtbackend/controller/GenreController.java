package com.example.eindopdrachtbackend.controller;

import com.example.eindopdrachtbackend.dto.input.GenreInputDto;
import com.example.eindopdrachtbackend.dto.output.GenreOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<GenreOutputDto> createGenre(@Valid @RequestBody GenreInputDto genreInputDto){
        GenreOutputDto genreOutputDto = genreService.createGenre(genreInputDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(genreOutputDto.getId()).toUri();
        return ResponseEntity.created(uri).body(genreOutputDto);
    }

    @GetMapping
    public ResponseEntity<List<GenreOutputDto>> returnAllGenres(){
        return ResponseEntity.ok().body(genreService.getAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreOutputDto> getGenreById(@PathVariable int id) throws RecordNotFoundException{
        return ResponseEntity.ok().body(genreService.getGenreById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreOutputDto> updateGenre(@PathVariable int id, @RequestBody GenreInputDto genre){
        return ResponseEntity.ok().body(genreService.updateGenre(id, genre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable int id){
        return ResponseEntity.ok().body(genreService.deleteGenre(id));
    }
}
