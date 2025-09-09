package com.SebasBarber.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException() {
        super("Error en el servicio.");
    }
}
