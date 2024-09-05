package com.example.eindopdrachtbackend.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageOutputDto {

    private Long id;
    private String title;
    private String url;
    private String contentType;
    private byte[] content;
}
