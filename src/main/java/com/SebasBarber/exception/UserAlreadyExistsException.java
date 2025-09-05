package com.SebasBarber.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {
        super("El número de teléfono ya está registrado.");
    }

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
