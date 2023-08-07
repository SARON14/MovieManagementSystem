package com.et.movies.exception;

public class APIRequestException extends RuntimeException {
    public APIRequestException(String message) {
        super(message);
    }
}
