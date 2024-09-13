package com.example.eindopdrachtbackend.service;

import com.example.eindopdrachtbackend.dto.input.ImageInputDto;
import com.example.eindopdrachtbackend.dto.mapper.ImageMapper;
import com.example.eindopdrachtbackend.dto.output.ImageOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.Image;
import com.example.eindopdrachtbackend.repository.ImageRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
@Service
public class ImageService{

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image storeFile(MultipartFile file, String url) throws IOException{

        String originalFileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        byte[] bytes = file.getBytes();

        Image image = new Image(originalFileName, contentType,url, bytes);
        return imageRepository.save(image);
    }

//    public ImageOutputDto createImage(ImageInputDto imageInputDto){
//        Image i = imageRepository.save(ImageMapper.fromInputDtoToModel(imageInputDto));
//        return ImageMapper.fromModelToOutputDto(i);
//    }

    public ImageOutputDto getImageById(long id){
        Optional<Image> i = imageRepository.findById(id);
        if (i.isPresent()){
            return ImageMapper.fromModelToOutputDto(i.get());
        } else {
            throw new RecordNotFoundException("No image with that id was found");
        }
    }

    public ImageOutputDto updateImage(@Valid long id, ImageInputDto imageInputDto){
        Optional<Image> i = imageRepository.findById(id);
        if(i.isPresent()){
            Image image = i.get();
            image.setTitle(imageInputDto.getTitle());
            image.setUrl(imageInputDto.getUrl());
            image.setContentType(imageInputDto.getContentType());
            image.setContent(imageInputDto.getContent());
            imageRepository.save(image);
            return ImageMapper.fromModelToOutputDto(image);
        } else {
            throw new RecordNotFoundException("No image with i "+id+" was found");
        }
    }

    public String deleteImage(long id){
        Optional<Image> i = imageRepository.findById(id);
        if(i.isPresent()){
            imageRepository.delete(i.get());
            return "Game with id " +id+" has been removed";
        } else {
            throw new RecordNotFoundException("No games with "+ id +" found");
        }
    }
}
