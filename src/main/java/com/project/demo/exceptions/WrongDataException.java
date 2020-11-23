package com.project.demo.exceptions;

public class WrongDataException extends RuntimeException {

    public WrongDataException(String message) {
        super(message);
    }
}
