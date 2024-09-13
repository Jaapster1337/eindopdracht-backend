package com.example.eindopdrachtbackend.controller;

import com.example.eindopdrachtbackend.dto.input.ImageInputDto;
import com.example.eindopdrachtbackend.dto.output.ImageOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.service.ImageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageOutputDto> getImageById(@Valid @PathVariable int id) throws RecordNotFoundException{
        return ResponseEntity.ok().body(imageService.getImageById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageOutputDto> updateImage(@Valid @PathVariable int id, @RequestBody ImageInputDto imageInputDto){
        return ResponseEntity.ok().body(imageService.updateImage(id, imageInputDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable long id){
        return ResponseEntity.ok().body(imageService.deleteImage(id));
    }
}
