package com.example.eindopdrachtbackend.controller;

import com.example.eindopdrachtbackend.dto.input.PublisherInputDto;
import com.example.eindopdrachtbackend.dto.output.PublisherOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable int id){
        return ResponseEntity.ok().body(publisherService.deletePublisher(id));
    }
}
