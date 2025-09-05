package com.SebasBarber.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "vip_turn")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VipAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", length = 10)
    private DayOfWeek dayOfWeek;

    @Column(name = "day_of_month")
    private int dayOfMonth;

    @NotNull
    @Column(nullable = false)
    private LocalTime time;

    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
