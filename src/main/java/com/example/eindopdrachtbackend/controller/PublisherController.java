package com.example.eindopdrachtbackend.controller;

import com.example.eindopdrachtbackend.dto.input.ImageInputDto;
import com.example.eindopdrachtbackend.dto.input.PublisherInputDto;
import com.example.eindopdrachtbackend.dto.output.PublisherOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.Image;
import com.example.eindopdrachtbackend.service.ImageService;
import com.example.eindopdrachtbackend.service.PublisherService;
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
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;
    private final ImageService imageService;

    public PublisherController(PublisherService publisherService, ImageService imageService) {
        this.publisherService = publisherService;
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<PublisherOutputDto> createPublisher(@Valid @RequestBody PublisherInputDto publisherInputDto){
        PublisherOutputDto publisherOutputDto = publisherService.createPublisher(publisherInputDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(publisherOutputDto.getId()).toUri();
        return ResponseEntity.created(uri).body(publisherOutputDto);
    }

    @GetMapping
    public ResponseEntity<List<PublisherOutputDto>> returnAllPublishers(){
        return ResponseEntity.ok().body(publisherService.getAllPublishers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherOutputDto> getPublisherById(@PathVariable int id) throws RecordNotFoundException{
        return ResponseEntity.ok().body(publisherService.getPublisherById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherOutputDto> updatePublisher(@PathVariable int id, @RequestBody PublisherInputDto publisher){
        return ResponseEntity.ok().body(publisherService.updatePublisher(id, publisher));
    }

    @PutMapping("/{pId}/image")
    public ResponseEntity<PublisherOutputDto> addImageToPublisher(@PathVariable Long pId, @RequestBody MultipartFile file) throws IOException{
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/publishers/")
                .path(Objects.requireNonNull(pId.toString()))
                .path("/image")
                .toUriString();

        Image image = imageService.storeFile(file, url);
        PublisherOutputDto publisher = publisherService.addImageToPublisher(pId, image);
        return ResponseEntity.created(URI.create(url)).body(publisher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable int id){
        return ResponseEntity.ok().body(publisherService.deletePublisher(id));
    }
}
