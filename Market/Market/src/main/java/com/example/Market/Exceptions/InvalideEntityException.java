package com.example.Market.Exceptions;

import lombok.Data;

import java.util.List;

@Data
public class InvalideEntityException extends RuntimeException{

    private ErrorsCode errorCode;
    private List<String> errors;

    public InvalideEntityException(String message, ErrorsCode errorCode, List<String> errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }
}
