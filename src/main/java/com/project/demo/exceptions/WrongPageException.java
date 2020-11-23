package com.project.demo.exceptions;

public class WrongPageException extends RuntimeException {

    public WrongPageException(String message) {
        super(message);
    }
}
