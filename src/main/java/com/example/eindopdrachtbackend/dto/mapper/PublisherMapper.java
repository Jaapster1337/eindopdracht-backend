package com.example.eindopdrachtbackend.dto.mapper;

import com.example.eindopdrachtbackend.dto.input.PublisherInputDto;
import com.example.eindopdrachtbackend.dto.output.PublisherOutputDto;
import com.example.eindopdrachtbackend.model.Publisher;

public class PublisherMapper {

    public static Publisher fromInputDtoToModel(PublisherInputDto publisherInputDto){
        Publisher p = new Publisher();
        p.setName(p.getName());
        p.setCreationDate(publisherInputDto.getCreationDate());
        p.setDescription(publisherInputDto.getDescription());
        p.setListOfGame(publisherInputDto.getListOfGame());
        return p;
    }

    public static PublisherOutputDto fromModelToOutPutDto(Publisher publisher){
        PublisherOutputDto publisherOutputDto = new PublisherOutputDto();
        publisherOutputDto.setId(publisher.getId());
        publisherOutputDto.setCreationDate(publisher.getCreationDate());
        publisherOutputDto.setDescription(publisher.getDescription());
        publisherOutputDto.setListOfGame(publisher.getListOfGame());
        return publisherOutputDto;
    }
}
