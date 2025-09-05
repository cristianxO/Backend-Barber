package com.SebasBarber.service;

import com.SebasBarber.DTO.UserDTO;
import com.SebasBarber.DTO.UtilDTO;
import com.SebasBarber.exception.UserAlreadyExistsException;
import com.SebasBarber.exception.UserNotFoundException;
import com.SebasBarber.repository.UserRepository;
import com.SebasBarber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilDTO utilDTO;

    public void saveUser(User user) {
        Optional<User> existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (!existingUser.isEmpty()) {
            throw new UserAlreadyExistsException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<UserDTO> getUser(String phone) {
        Optional<User> existingUser = userRepository.findByPhoneNumber(phone);
        if (!existingUser.isEmpty()) {
            return utilDTO.mapUserToUserDTO(existingUser.get());
        }
        throw new UserNotFoundException();
    }

    public void updateUser(User user) {
        Optional<User> existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (!existingUser.isEmpty()) {
            User temUser = existingUser.get();
            temUser.setName(user.getName());
            temUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(temUser);
        }
        throw new UserNotFoundException();
    }

    public List<Optional<UserDTO>> getUsers() {
        return userRepository.findAll().stream()
                .map(user -> utilDTO.mapUserToUserDTO(user))
                .toList();
    }

    public void deleteUser(String phone) {
        Optional<User> existingUser = userRepository.findByPhoneNumber(phone);
        if (!existingUser.isEmpty()) {
            userRepository.deleteByPhoneNumber(phone);
        }
        throw new UserNotFoundException();
    }
}
