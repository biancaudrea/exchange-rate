package com.betvictor.exchangerate.exception;

public class EmptyServerResponse extends RuntimeException {
    private String message;

    public EmptyServerResponse(String message) {
        super(message);
    }
}
