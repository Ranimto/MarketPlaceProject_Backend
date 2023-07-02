package com.example.Market.Exceptions;

import lombok.Data;

@Data
public class EntityNotFoundException extends RuntimeException{

    private ErrorsCode errorCode;

    public EntityNotFoundException(String message, ErrorsCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
