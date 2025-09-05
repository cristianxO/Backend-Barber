package com.SebasBarber.DTO;

import com.SebasBarber.model.User;

import java.util.Optional;

public class UtilDTO {
    public User mapToUser(UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .phoneNumber(userDTO.getPhoneNumber())
                .role(userDTO.getRole())
                .build();
        return user;
    }

    public Optional<UserDTO> mapUserToUserDTO(User user) {
        UserDTO userDTO = UserDTO.builder()
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
        return Optional.of(userDTO);
    }
}
