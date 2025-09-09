package com.SebasBarber.exception;

public class AppointmentGapNotAllowedException extends RuntimeException {
    public AppointmentGapNotAllowedException(String message) {
        super(message);
    }

    public AppointmentGapNotAllowedException() {
        super("Debe correr su cita 30 minutos antes o después.");
    }
}
