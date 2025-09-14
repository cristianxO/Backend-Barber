package com.SebasBarber.repository;

import com.SebasBarber.model.Appointment;
import com.SebasBarber.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByDateOrderByHourAsc(LocalDate date);
    Optional<Appointment> findByUserAndDate(User user, LocalDate date);
}
