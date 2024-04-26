package com.example.exception;

public class FulfillLogicException extends RuntimeException{
    public FulfillLogicException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
