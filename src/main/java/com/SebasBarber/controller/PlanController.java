package com.SebasBarber.controller;

import com.SebasBarber.DTO.PlanDTO;
import com.SebasBarber.DTO.ServiceDTO;
import com.SebasBarber.model.Plan;
import com.SebasBarber.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("plan")
public class PlanController {

    @Autowired
    PlanService planService;

    @PostMapping
    public ResponseEntity<Void> createPlan(@RequestBody Plan plan) {
        planService.createPlan(plan);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public Optional<PlanDTO> getPlanById(@PathVariable int id) {
        return planService.getPlanById(id);
    }

    @GetMapping
    public List<Plan> getPlans() {
        return planService.getPlans();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePlan(@PathVariable int id, @RequestBody Plan plan) {
        planService.updatePlan(id,plan);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/services/all/{id}")
    public ResponseEntity<Void> updatePlanService(@PathVariable int id, @RequestBody List<ServiceDTO> services) {
        planService.updateAllPlanServices(id,services);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/services/add/{id}")
    public ResponseEntity<Void> addPlanServices(@PathVariable int id, @RequestBody List<ServiceDTO> services) {
        planService.addPlanServices(id,services);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/services/remove/{id}")
    public ResponseEntity<Void> removePlanServices(@PathVariable int id, @RequestBody List<ServiceDTO> services) {
        planService.removePlanServices(id,services);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<Void> activatePlan(@PathVariable int id) {
        planService.activatePlan(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Void> deactivatePlan(@PathVariable int id) {
        planService.deactivatePlan(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable int id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}
