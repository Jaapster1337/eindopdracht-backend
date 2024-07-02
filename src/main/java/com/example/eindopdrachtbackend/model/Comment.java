package com.example.eindopdrachtbackend.model;

import java.time.LocalDate;

public class Comment {
    private Long id;
    private User user;
    private String content;
    private LocalDate postDate;
    private Long amountOfLikes;
}
