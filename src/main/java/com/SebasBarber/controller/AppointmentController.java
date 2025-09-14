package com.SebasBarber.controller;

import com.SebasBarber.DTO.AppointmentDTO;
import com.SebasBarber.model.Appointment;
import com.SebasBarber.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Void> createAppointment(@RequestBody Appointment appointment) {
        appointmentService.saveAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public List<AppointmentDTO> getAppointmentsForToday() {
        return appointmentService.getAppointmentsForToday();
    }

    @GetMapping("/{date}")
    public List<AppointmentDTO> getAppointmentsByDate(@PathVariable LocalDate date) {
        return appointmentService.getAppointmentsByDate(date);
    }

    @DeleteMapping("/{phone}/{date}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable String phone, @PathVariable LocalDate date ) {
        appointmentService.deleteAppointment(phone,date);
        return ResponseEntity.noContent().build();
    }
}
