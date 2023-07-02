package com.example.Market.Exceptions;

public enum ErrorsCode {
    PRODUCT_NOT_FOUND(1000),
    PRODUCT_NOT_VALID(1001),

    USER_NOT_FOUND(12000),
    USER_NOT_VALID(12001),
    USER_ALREADY_EXISTS(12002),

    LIGNE_COMMANDE__NOT_FOUND(8000),
    LIGNE_COMMANDE__NOT_VALID(8000),

    COMMANDE_NOT_FOUND(8000),
    COMMANDE_NOT_VALID(8000),

    ;

    private int code;

    ErrorsCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}


