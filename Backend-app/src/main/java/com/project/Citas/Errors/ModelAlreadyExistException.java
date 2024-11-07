package com.project.Citas.Errors;

public class ModelAlreadyExistException extends RuntimeException{
    public ModelAlreadyExistException(String message) {
        super(message);
    } 
}
