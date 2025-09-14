package com.SebasBarber.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServiceDTO {
    private int id;
    private String description;
    private double price;
    private int duration;
    private boolean isActive;
}
