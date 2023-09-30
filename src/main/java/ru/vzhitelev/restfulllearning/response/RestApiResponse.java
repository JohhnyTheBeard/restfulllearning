package ru.vzhitelev.restfulllearning.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiResponse {
    private int status;
    private String message;
    private Object data;

    public RestApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public RestApiResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
