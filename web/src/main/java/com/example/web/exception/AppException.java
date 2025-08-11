package com.example.web.exception;

import com.nimbusds.jose.KeyLengthException;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppException extends RuntimeException {
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AppException(ErrorCode tokenGenerationError, String string, KeyLengthException e) {
        // TODO Auto-generated constructor stub
    }

    private ErrorCode errorCode;

}
