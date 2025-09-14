package com.SebasBarber.controller;

import com.SebasBarber.DTO.ServiceDTO;
import com.SebasBarber.model.ServiceModel;
import com.SebasBarber.service.ServiceModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("service")
public class ServiceController {

    @Autowired
    ServiceModelService serviceModelService;

    @PostMapping
    public ResponseEntity<Void> createService(@RequestBody ServiceModel service) {
        serviceModelService.createService(service);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public Optional<ServiceDTO> getServiceById(@PathVariable int id) {
        return serviceModelService.getServiceById(id);
    }

    @GetMapping
    public List<ServiceDTO> getServices() {
        return serviceModelService.getServices();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateService(@PathVariable int id, @RequestBody ServiceModel service) {
        serviceModelService.updateService(id, service);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<Void> activateService(@PathVariable int id) {
        serviceModelService.activateService(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Void> deactivateService(@PathVariable int id) {
        serviceModelService.deactivateService(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable int id) {
        serviceModelService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
