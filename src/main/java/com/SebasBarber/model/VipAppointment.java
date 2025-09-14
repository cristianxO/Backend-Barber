package com.SebasBarber.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Min(1) @Max(28)
    @Column(name = "day_of_month")
    private Integer dayOfMonth;

    private boolean isEndOfMonth;

    @NotNull
    @Column(nullable = false)
    private LocalTime hour;

    private Boolean isActive;

    private int slots;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
