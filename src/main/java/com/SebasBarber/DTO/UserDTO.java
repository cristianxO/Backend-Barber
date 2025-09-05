package com.SebasBarber.DTO;

import com.SebasBarber.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    private String name;
    private String phoneNumber;
    private Role role;
}
