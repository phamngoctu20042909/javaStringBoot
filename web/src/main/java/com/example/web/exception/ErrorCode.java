package com.example.web.exception;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(99999, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(99998, "User not found", HttpStatus.NOT_FOUND),
    USER_NOT_VALID(00,
            "User's information is not valid,Username must be at least 6 and password must be at least 5  character!!!",
            HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(99997, "Not the Author", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(99996, "Must not access", HttpStatus.FORBIDDEN),
    TOKEN_GENERATION_ERROR(99995, "Can create token, please try again later!", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private HttpStatusCode statusCode;
    private int code;
    private String message;

}
