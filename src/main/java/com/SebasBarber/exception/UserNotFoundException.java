package com.SebasBarber.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
      super("El usuario no se encuentra registrado.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
