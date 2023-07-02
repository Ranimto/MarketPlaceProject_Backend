package com.example.Market.Exceptions;

import lombok.Data;

@Data
public class InvalideOperationException extends RuntimeException{
    private ErrorsCode errorCode;

    public InvalideOperationException(String message, ErrorsCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
