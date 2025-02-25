package com.example.teste_agro_e.shared.exceptions;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private String errorCode;
    private String message;

    public CustomException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
