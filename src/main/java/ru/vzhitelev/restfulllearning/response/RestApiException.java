package ru.vzhitelev.restfulllearning.response;

public class RestApiException extends RuntimeException {
    public RestApiException(String message) {
        super(message);
    }
}
