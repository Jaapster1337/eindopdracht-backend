package com.example.eindopdrachtbackend.dto.mapper;

import com.example.eindopdrachtbackend.dto.input.ImageInputDto;
import com.example.eindopdrachtbackend.dto.output.ImageOutputDto;
import com.example.eindopdrachtbackend.model.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    public static Image fromInputDtoToModel(ImageInputDto imageInputDto){
        Image i = new Image();
        i.setTitle(imageInputDto.getTitle());
        i.setUrl(imageInputDto.getUrl());
        i.setContent(imageInputDto.getContent());
        i.setContentType(imageInputDto.getContentType());
        return i;
    }

    public static ImageOutputDto fromModelToOutputDto(Image image){
        ImageOutputDto imageOutputDto = new ImageOutputDto();
        imageOutputDto.setId(image.getId());
        imageOutputDto.setUrl(image.getUrl());
        imageOutputDto.setContentType(image.getContentType());
        imageOutputDto.setTitle(image.getTitle());
        imageOutputDto.setContent(image.getContent());
        return imageOutputDto;
    }
}
