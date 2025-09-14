package com.SebasBarber.exception;

public class PlanNotFoundException extends RuntimeException {
    public PlanNotFoundException(String message) {
        super(message);
    }

    public PlanNotFoundException() {
        super("El plan no fue encontrado.");
    }
}
