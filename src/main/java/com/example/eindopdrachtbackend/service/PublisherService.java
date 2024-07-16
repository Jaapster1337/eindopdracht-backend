package com.example.eindopdrachtbackend.service;

import com.example.eindopdrachtbackend.dto.input.PublisherInputDto;
import com.example.eindopdrachtbackend.dto.mapper.PublisherMapper;
import com.example.eindopdrachtbackend.dto.output.PublisherOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.Publisher;
import com.example.eindopdrachtbackend.repository.PublisherRepository;
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

    public PublisherOutputDto createPublisher(PublisherInputDto publisherInputDto){
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

    public PublisherOutputDto updatePublisher(long id, PublisherInputDto publisherInputDto){
        Optional<Publisher> p = publisherRepository.findById(id);
        if(p.isPresent()) {
           p.get().setName(publisherInputDto.getName());
           p.get().setCreationDate(publisherInputDto.getCreationDate());
           p.get().setDescription(publisherInputDto.getDescription());
           p.get().setListOfGame(publisherInputDto.getListOfGame());
           publisherRepository.save(p.get());
           return PublisherMapper.fromModelToOutPutDto(p.get());
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


}
