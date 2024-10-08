package com.example.eindopdrachtbackend.service;

import com.example.eindopdrachtbackend.dto.input.PublisherInputDto;
import com.example.eindopdrachtbackend.dto.mapper.PublisherMapper;
import com.example.eindopdrachtbackend.dto.output.PublisherOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.Image;
import com.example.eindopdrachtbackend.model.Publisher;
import com.example.eindopdrachtbackend.repository.PublisherRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<PublisherOutputDto> getAllPublishers(){
        Publisher p = new Publisher();
        List<Publisher> allPublishers  = publisherRepository.findAll();
        List<PublisherOutputDto> allPublisherOutputList = new ArrayList<>();
        for(Publisher publisher: allPublishers){
            allPublisherOutputList.add(PublisherMapper.fromModelToOutPutDto(publisher));
        }
        return allPublisherOutputList;
    }

    public PublisherOutputDto createPublisher(@Valid PublisherInputDto publisherInputDto){
        Publisher p = publisherRepository.save(PublisherMapper.fromInputDtoToModel(publisherInputDto));
        return PublisherMapper.fromModelToOutPutDto(p);
    }

    public PublisherOutputDto getPublisherById(long id){
        Optional<Publisher> p = publisherRepository.findById(id);
        if(p.isPresent()){
            return PublisherMapper.fromModelToOutPutDto(p.get());
        } else {
            throw new RecordNotFoundException("No publisher with "+id+ " was found");
        }
    }

    public PublisherOutputDto updatePublisher(@Valid long id, PublisherInputDto publisherInputDto){
        Optional<Publisher> p = publisherRepository.findById(id);
        if(p.isPresent()) {
           Publisher publisher = p.get();
           publisher.setName(publisherInputDto.getName());
           publisher.setCreationDate(publisherInputDto.getCreationDate());
           publisher.setDescription(publisherInputDto.getDescription());
           publisher.setListOfGame(publisherInputDto.getListOfGame());
           publisherRepository.save(publisher);
           return PublisherMapper.fromModelToOutPutDto(publisher);
        } else {
            throw new RecordNotFoundException("No publisher with id " + id + " found");
        }
    }

    public String deletePublisher(long id){
        Optional<Publisher> p = publisherRepository.findById(id);
        if(p.isPresent()){
            publisherRepository.delete(p.get());
            return "Publisher with id " + id + " has been removed";
        } else {
            throw new RecordNotFoundException("No publisher with id " + id + " found");
        }
    }

    @Transactional
    public PublisherOutputDto addImageToPublisher(@Valid Long pId, Image image){
        Optional<Publisher> optionalPublisher = publisherRepository.findById(pId);
        if (optionalPublisher.isEmpty()){
            throw new RecordNotFoundException("Publisher with id "+pId+" not found");
        }
        Publisher publisher = optionalPublisher.get();
        publisher.setPublisherLogo(image);
        publisherRepository.save(publisher);
        return PublisherMapper.fromModelToOutPutDto(publisher);
    }

}
