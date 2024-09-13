package com.example.eindopdrachtbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @NotBlank
    private String url;
    @NotBlank
    private String contentType;
    @Lob
    private byte[] content;
    public Image(String title, String url, String contentType, byte[] content) {
        this.title = title;
        this.url = url;
        this.contentType = contentType;
        this.content = content;
    }


    public Image() {

    }
}
