package com.SebasBarber.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@Table(name = "service")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(nullable = false)
    private String description;

    //Duración en minutos
    @NotNull
    @Column(nullable = false)
    private int duration;

    @NotNull
    @Column(nullable = false)
    private double price;

    private boolean isActive;

    @ManyToMany(mappedBy = "services")
    private List<Appointment> appointments;

    @ManyToMany(mappedBy = "services")
    private List<Plan> plans;
}
