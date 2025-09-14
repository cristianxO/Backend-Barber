package com.SebasBarber.DTO;

import com.SebasBarber.model.*;

import java.time.LocalDate;

public class UtilDTO {

    public User mapToUser(UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .phoneNumber(userDTO.getPhoneNumber())
                .role(userDTO.getRole())
                .build();
        return user;
    }

    public UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = UserDTO.builder()
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
        return userDTO;
    }

    public AppointmentDTO mapAppointmentToAppointmentDTO(Appointment appointment) {
        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .date(appointment.getDate())
                .price(appointment.getTotal())
                .status(appointment.getStatus())
                .time(appointment.getHour())
                .userDTO(mapUserToUserDTO(appointment.getUser()))
                .build();
        return appointmentDTO;
    }

    public AppointmentDTO mapVipAppointmentToAppointmentDTO(VipAppointment vipAppointment) {
        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .date(LocalDate.now())
                .time(vipAppointment.getHour())
                .userDTO(mapUserToUserDTO(vipAppointment.getUser()))
                .build();
        return appointmentDTO;
    }

    public ServiceDTO mapServiceToServiceDTO(ServiceModel service) {
        ServiceDTO serviceDTO = ServiceDTO.builder()
                .id(service.getId())
                .description(service.getDescription())
                .duration(service.getDuration())
                .isActive(service.getIsActive())
                .price(service.getPrice())
                .build();
        return serviceDTO;
    }

    public PlanDTO mapPlanToPlanDTO(Plan plan) {
        PlanDTO planDTO = PlanDTO.builder()
                .id(plan.getId())
                .description(plan.getDescription())
                .isActive(plan.getIsActive())
                .price(plan.getPrice())
                .services(plan.getServices().stream()
                        .map(service -> mapServiceToServiceDTO(service)).toList())
                .build();
        return planDTO;
    }
}
