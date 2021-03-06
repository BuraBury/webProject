package com.project.demo.controller;

import com.project.demo.exceptions.WrongPageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.project.demo.model.Error;

@RestControllerAdvice
public class GlobalExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public Error pageExceptionHandler(WrongPageException wrongPageException) {
        return new Error(wrongPageException.getMessage());
    }
}
