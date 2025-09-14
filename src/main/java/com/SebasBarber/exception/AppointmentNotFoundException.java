package com.SebasBarber.exception;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
    public AppointmentNotFoundException() {
        super("No se ha podido encontrar la cita.");
    }
}
