package com.SebasBarber.exception;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(String message) {
        super(message);
    }

    public ServiceNotFoundException() {
        super("El servicio no fue encontrado");
    }
}
