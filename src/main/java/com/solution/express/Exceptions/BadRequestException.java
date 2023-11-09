package com.solution.express.Exceptions;


public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
