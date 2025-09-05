package com.SebasBarber.DTO;

import com.SebasBarber.model.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDTO {
    private LocalDate date;
    private LocalTime time;
    private AppointmentStatus status;
    private double price;
}
