package com.SebasBarber.service;

import com.SebasBarber.DTO.AppointmentDTO;
import com.SebasBarber.DTO.UtilDTO;
import com.SebasBarber.exception.*;
import com.SebasBarber.model.*;
import com.SebasBarber.repository.AppointmentRepository;
import com.SebasBarber.repository.UserRepository;
import com.SebasBarber.repository.VipAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    VipAppointmentRepository vipAppointmentRepository;

    @Autowired
    UtilDTO utilDTO;

    @Autowired
    UserRepository userRepository;

    /**
     *  1 7:00 - 7:30
     *  2 7:30 - 8:00
     *  3 8:00 - 8:30
     *  4 8:30 - 9:00
     *  5 9:00 - 9:30
     *  6 9:30 - 10:00
     *  7 10:00 - 10:30
     *  8 10:30 - 11:00
     *  9 11:00 - 11:30
     *  10 11:30 - 12:00
     *      Almuerzo
     *  11 2:00 - 2:30
     *  12 2:30 - 3:00
     *  13 3:00 - 3:30
     *  14 3:30 - 4:00
     *  15 4:00 - 4:30
     *  16 4:30 - 5:00
     *  17 5:00 - 5:30
     *  18 5:30 - 6:00
     *  19 6:00 - 6:30
     *  20 6:30 - 7:00
     *  21 7:00 - 7:30
     *  22 7:30 - 8:00
     *  23 8:00 - 8:30
     *  24 8:30 - 9:00
     */

    public void saveAppointment(Appointment appointment) {
        Optional<User> existingUser = userRepository.findByPhoneNumber(appointment.getUser().getPhoneNumber());
        if (!existingUser.isEmpty()) {
            if (isDateInThePast(appointment.getDate(),appointment.getHour())) {
                if (validateBusinessHours(appointment.getDate(), appointment.getHour())) {
                    if (isPlanAndServiceNotNull(appointment.getPlan(),appointment.getServices())) {
                        appointment.setDuration(calculateAppointmentDuration(appointment));
                        appointment.setSlots(appointment.getDuration()/30);
                        if (isDateAvailable(appointment)) {
                            appointment.setStatus(AppointmentStatus.RESERVADA);
                            appointment.setTotal(calculateTotal(appointment.getPlan(),appointment.getServices()));
                            appointmentRepository.save(appointment);
                        }
                        throw new DateIsNotAvailableException();
                    }
                    throw new ServiceException();
                }
                throw new InvalidAppointmentDateException();
            }
            throw new InvalidAppointmentDateException();
        }
        throw new UserNotFoundException();
    }

    private boolean isDateInThePast(LocalDate date, LocalTime hour) {
        LocalDate dateToCompare = LocalDate.now();
        if (date.isAfter(dateToCompare)) {
            return true;
        } else if (date.isEqual(dateToCompare)) {
            if (LocalTime.now().isBefore(hour)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlanAndServiceNotNull(Plan plan, List<ServiceModel> services) {
        if (plan == null) {
            if (services == null) {
                return false;
            }
            return true;
        }else if (services == null) {
            return true;
        }
        return true;
    }

    private boolean validateBusinessHours(LocalDate date, LocalTime time) {
        if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            return false;
        }
        int hour = time.getHour();
        if (hour >= 7 && hour <= 12) {
            if (hour == 12 && time.getMinute() == 0) {
                return true;
            }else if (hour == 12) {
                return false;
            }
            return true;
        }
        if (hour >=14 && hour <= 21) {
            if (hour == 21 && time.getMinute() == 0) {
                return true;
            }else if (hour == 21) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean isDateAvailable(Appointment appointment) {
        List<Appointment> appointments = appointmentRepository.findByDateOrderByHourAsc(appointment.getDate());
        List<VipAppointment> vipAppointments = vipAppointmentRepository.findByIsActiveTrueAndDate(appointment.getDate().getDayOfMonth());
        vipAppointments.addAll(vipAppointmentRepository.findByIsActiveTrueAndDayOfWeek(appointment.getDate().getDayOfWeek()));
        if (appointment.getDate().lengthOfMonth() == appointment.getDate().getDayOfMonth()) {
            vipAppointments.addAll(vipAppointmentRepository.findByIsActiveTrueAndIsEndOfMonthTrue());
        }
        boolean[] occupied = new boolean[24];
        //Mapea todas las citas normales del dia en un arreglo
        for (Appointment aux: appointments) {
            int slot = calculateSlots(aux.getHour());
            for (int i = 0; i < aux.getSlots(); i++) {
                occupied[slot + i] = true;
            }
        }
        //Mapea todas las citas vip del dia en un arreglo
        for (VipAppointment aux: vipAppointments) {
            int slot = calculateSlots(aux.getHour());
            for (int i = 0; i < aux.getSlots(); i++) {
                occupied[slot + i] = true;
            }
        }
        //Valida si la cita del usuario cabe dentro del tiempo libre
        int slot = calculateSlots(appointment.getHour());
        for (int i = 0; i < appointment.getSlots(); i++) {
            if (occupied[slot + i]) {
                return false;
            }
        }
        if (canPlaceAppointmentWithoutGap(appointment.getSlots(),slot,occupied)) {
            throw new AppointmentGapNotAllowedException();
        }
        return true;
    }

    private boolean canPlaceAppointmentWithoutGap(int slots, int position, boolean[] occupied) {
        if (!occupied[position-1]) {
            if ((slots+position+1) >= occupied.length) {
                return false;
            }
            if (!occupied[position+slots+1]) {
                return true;
            }
        }
        return false;
    }

    private int calculateSlots(LocalTime time) {
        int slot = 0;
        if (time.getHour() < 12) {
            slot = ((time.getHour() - 7) * 2);
        }
        else {
            slot = ((time.getHour() - 14) * 2) + 10;
        }
        if (time.getMinute() == 30) {
            slot++;
        }
        return slot;
    }

    private double calculateTotal(Plan plan, List<ServiceModel> services) {
        double total = 0;
        if (plan != null) {
            total += plan.getPrice();
        }
        if (services != null) {
            for (int i = 0; i < services.size(); i++) {
                total += services.get(i).getPrice();
            }
        }
        return total;
    }

    private int calculateAppointmentDuration(Appointment appointment) {
        int totalMinutes = 0;
        // Duración del plan
        if (appointment.getPlan() != null) {
            totalMinutes += appointment.getPlan().getDuration();
        }
        // Duración de los servicios
        if (appointment.getServices() != null) {
            totalMinutes += appointment.getServices()
                    .stream()
                    .mapToInt(ServiceModel::getDuration)
                    .sum();
        }

        int remainder = totalMinutes % 30;
        if (remainder < 15) {
            totalMinutes -= remainder;
        } else {
            totalMinutes += (30 - remainder);
        }
        return totalMinutes;
    }

    public List<AppointmentDTO> getAppointmentsForToday() {
        LocalDate date = LocalDate.now();
        //Citas normales
        List<AppointmentDTO> appointments = appointmentRepository.findByDateOrderByHourAsc(date)
                .stream().map(appointment -> utilDTO.mapAppointmentToAppointmentDTO(appointment)).toList();
        //Citas VIP activas por fecha
        appointments.addAll(vipAppointmentRepository.findByIsActiveTrueAndDate(date.getDayOfMonth())
                .stream().map(vipAppointment -> utilDTO.mapVipAppointmentToAppointmentDTO(vipAppointment)).toList());
        //Citas VIP activas por dia de la semana
        appointments.addAll(vipAppointmentRepository.findByIsActiveTrueAndDayOfWeek(date.getDayOfWeek())
                .stream().map(vipAppointment -> utilDTO.mapVipAppointmentToAppointmentDTO(vipAppointment)).toList());
        if (date.lengthOfMonth() == date.getDayOfMonth()) {
            //Citas VIP activas de fin de mes
            appointments.addAll(vipAppointmentRepository.findByIsActiveTrueAndIsEndOfMonthTrue()
                    .stream().map(vipAppointment -> utilDTO.mapVipAppointmentToAppointmentDTO(vipAppointment)).toList());
        }
        return appointments;
    }

    public List<AppointmentDTO> getAppointmentsByDate(LocalDate date) {
        //Citas normales
        List<AppointmentDTO> appointments = appointmentRepository.findByDateOrderByHourAsc(date)
                .stream().map(appointment -> utilDTO.mapAppointmentToAppointmentDTO(appointment)).toList();
        //Citas VIP activas por fecha
        appointments.addAll(vipAppointmentRepository.findByIsActiveTrueAndDate(date.getDayOfMonth())
                .stream().map(vipAppointment -> utilDTO.mapVipAppointmentToAppointmentDTO(vipAppointment)).toList());
        //Citas VIP activas por dia de la semana
        appointments.addAll(vipAppointmentRepository.findByIsActiveTrueAndDayOfWeek(date.getDayOfWeek())
                .stream().map(vipAppointment -> utilDTO.mapVipAppointmentToAppointmentDTO(vipAppointment)).toList());
        if (date.lengthOfMonth() == date.getDayOfMonth()) {
            //Citas VIP activas de fin de mes
            appointments.addAll(vipAppointmentRepository.findByIsActiveTrueAndIsEndOfMonthTrue()
                    .stream().map(vipAppointment -> utilDTO.mapVipAppointmentToAppointmentDTO(vipAppointment)).toList());
        }
        return appointments;
    }

    public void deleteAppointment(String phone, LocalDate date) {
        Optional<User> existingUser = userRepository.findByPhoneNumber(phone);
        if (!existingUser.isEmpty()) {
            Optional<Appointment> existingAppointment = appointmentRepository.findByUserAndDate(existingUser.get(),date);
            if (!existingAppointment.isEmpty()) {
                appointmentRepository.delete(existingAppointment.get());
            }
            throw new AppointmentNotFoundException();
        }
        throw new UserNotFoundException();
    }
}
