package com.SebasBarber.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlanDTO {
    private int id;
    private String description;
    private double price;
    private boolean isActive;
    private List<ServiceDTO> services;
}
