package com.example.teste_agro_e.configuration;

import com.example.teste_agro_e.dtos.ErrorDTO;
import com.example.teste_agro_e.shared.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDTO> handleCustomException(CustomException ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        ErrorDTO errorDTO = new ErrorDTO("GENERAL_ERROR", "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.");
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
