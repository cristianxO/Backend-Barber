package com.SebasBarber.DTO;

import com.SebasBarber.model.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentDTO {
    private UserDTO userDTO;
    private LocalDate date;
    private LocalTime time;
    private AppointmentStatus status;
    private double price;
}
