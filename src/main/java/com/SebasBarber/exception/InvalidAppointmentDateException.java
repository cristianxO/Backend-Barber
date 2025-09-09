package com.SebasBarber.exception;

public class InvalidAppointmentDateException extends RuntimeException {
    public InvalidAppointmentDateException(String message) {
        super(message);
    }

    public InvalidAppointmentDateException() {
        super("Error en la fecha de la cita.");
    }
}
