package com.SebasBarber.exception;

public class DateIsNotAvailableException extends RuntimeException {
    public DateIsNotAvailableException(String message) {
        super(message);
    }

    public DateIsNotAvailableException() {
        super("Ya se encuentra agendado un turno en esta fecha y hora");
    }
}
