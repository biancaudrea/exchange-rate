package com.betvictor.exchangerate.service;


import com.betvictor.exchangerate.exception.EmptyServerResponse;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleException(Exception ex, WebRequest request) {
        String bodyOfResponse = "";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JSONException.class)
    protected ResponseEntity<Object> handleJSONException(JSONException ex, WebRequest request) {
        String bodyOfResponse = "Error parsing json: " + ex.getMessage();
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyServerResponse.class)
    protected ResponseEntity<Object> handleJSONException(EmptyServerResponse ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }

}
