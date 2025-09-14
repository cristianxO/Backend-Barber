package com.SebasBarber.controller;

import com.SebasBarber.DTO.UserDTO;
import com.SebasBarber.service.UserService;
import com.SebasBarber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{phone}")
    public Optional<UserDTO> getUser(@PathVariable String phone) {
        return userService.getUser(phone);
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/{phone}")
    public ResponseEntity<Void> deleteUser(@PathVariable String phone) {
        userService.deleteUser(phone);
        return ResponseEntity.noContent().build();
    }
}
