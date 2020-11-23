package com.project.demo.exceptions;

public class WrongIdException extends RuntimeException {

    public WrongIdException(String message) {
        super(message);
    }
}
