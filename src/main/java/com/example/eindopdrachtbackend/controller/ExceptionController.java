package com.example.eindopdrachtbackend.controller;


import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> exception(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(exception.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
    }
}
