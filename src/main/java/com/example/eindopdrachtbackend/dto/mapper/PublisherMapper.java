package com.example.eindopdrachtbackend.dto.mapper;

import com.example.eindopdrachtbackend.dto.input.PublisherInputDto;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.dto.output.PublisherOutputDto;
import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.Publisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PublisherMapper {

    private static GameMapper gameMapper;

    public PublisherMapper(GameMapper gameMapper) {
        this.gameMapper = gameMapper;
    }

    public static Publisher fromInputDtoToModel(PublisherInputDto publisherInputDto){
        Publisher p = new Publisher();
        p.setName(publisherInputDto.getName());
        p.setCreationDate(publisherInputDto.getCreationDate());
        p.setDescription(publisherInputDto.getDescription());
        p.setListOfGame(publisherInputDto.getListOfGame());
        return p;
    }

    public static PublisherOutputDto fromModelToOutPutDto(Publisher publisher){
        PublisherOutputDto publisherOutputDto = new PublisherOutputDto();
        publisherOutputDto.setId(publisher.getId());
        publisherOutputDto.setName(publisher.getName());
        publisherOutputDto.setCreationDate(publisher.getCreationDate());
        publisherOutputDto.setDescription(publisher.getDescription());
        List<GameOutputDto> gameOutputDtoList = new ArrayList<>();
        for (Game game : publisher.getListOfGame()) {
            gameOutputDtoList.add(gameMapper.fromModelToOutputDto(game));
        }
        publisherOutputDto.setListOfGame(gameOutputDtoList);
        if(publisher.getPublisherLogo() != null){
            publisherOutputDto.setPublisherLogo(ImageMapper.fromModelToOutputDto(publisher.getPublisherLogo()));
        }
        return publisherOutputDto;
    }
}
