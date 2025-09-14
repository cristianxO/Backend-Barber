package com.SebasBarber.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(nullable = false)
    private String description;

    //Duración en minutos
    @NotNull
    private int duration;

    @NotNull
    @Column(nullable = false)
    private double price;

    private Boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "plan_service",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<ServiceModel> services;

    @OneToMany(mappedBy = "plan")
    private List<Appointment> appointments;
}
