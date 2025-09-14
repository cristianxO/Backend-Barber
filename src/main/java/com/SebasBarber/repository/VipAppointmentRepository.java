package com.SebasBarber.repository;

import com.SebasBarber.model.VipAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface VipAppointmentRepository extends JpaRepository<VipAppointment, Integer> {

    List<VipAppointment> findByIsActiveTrueAndDate(Integer dayOfMonth);

    List<VipAppointment> findByIsActiveTrueAndDayOfWeek(DayOfWeek dayOfWeek);

    List<VipAppointment> findByIsActiveTrueAndIsEndOfMonthTrue();

}
