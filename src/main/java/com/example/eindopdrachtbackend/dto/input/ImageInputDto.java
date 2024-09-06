package com.example.eindopdrachtbackend.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ImageInputDto {
    private String title;
    private String url;
    private String contentType;
    private byte[] content;
}
