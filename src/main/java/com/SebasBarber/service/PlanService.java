package com.SebasBarber.service;

import com.SebasBarber.DTO.PlanDTO;
import com.SebasBarber.DTO.ServiceDTO;
import com.SebasBarber.DTO.UtilDTO;
import com.SebasBarber.exception.PlanNotFoundException;
import com.SebasBarber.exception.ServiceNotFoundException;
import com.SebasBarber.model.Plan;
import com.SebasBarber.model.ServiceModel;
import com.SebasBarber.repository.PlanRepository;
import com.SebasBarber.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    @Autowired
    PlanRepository planRepository;

    @Autowired
    UtilDTO utilDTO;

    @Autowired
    ServiceRepository serviceRepository;

    public void createPlan(Plan plan) {
        plan.setIsActive(true);
        planRepository.save(plan);
    }

    public Optional<PlanDTO> getPlanById(int id) {
        return planRepository.findById(id).map(plan -> utilDTO.mapPlanToPlanDTO(plan));
    }

    public List<Plan> getPlans() {
        return planRepository.findAll();
    }

    public void updatePlan(int id, Plan plan) {
        Optional<Plan> existingPlan = planRepository.findById(id);
        if (!existingPlan.isEmpty()) {
            Plan tempPlan = existingPlan.get();
            tempPlan.setDescription(plan.getDescription());
            tempPlan.setDuration(plan.getDuration());
            tempPlan.setPrice(plan.getPrice());
            planRepository.save(tempPlan);
        }
        throw new PlanNotFoundException();
    }

    public void activatePlan(int id) {
        Optional<Plan> existingPlan = planRepository.findById(id);
        if (!existingPlan.isEmpty()) {
            existingPlan.get().setIsActive(true);
            planRepository.save(existingPlan.get());
        }
        throw new PlanNotFoundException();
    }

    public void deactivatePlan(int id) {
        Optional<Plan> existingPlan = planRepository.findById(id);
        if (!existingPlan.isEmpty()) {
            existingPlan.get().setIsActive(false);
            planRepository.save(existingPlan.get());
        }
        throw new PlanNotFoundException();
    }

    public void deletePlan(int id) {
        Optional<Plan> existingPlan = planRepository.findById(id);
        if (!existingPlan.isEmpty()) {
            planRepository.delete(existingPlan.get());
        }
        throw new PlanNotFoundException();
    }

    public void updateAllPlanServices(int id, List<ServiceDTO> services) {
        List<ServiceModel> auxServices = new ArrayList<>();
        Optional<Plan> existingPlan = planRepository.findById(id);
        if (!existingPlan.isEmpty()) {
            for (ServiceDTO aux : services) {
                Optional<ServiceModel> existingService = serviceRepository.findById(aux.getId());
                if (!existingService.isEmpty()) {
                    auxServices.add(existingService.get());
                }
                throw new ServiceNotFoundException();
            }
            existingPlan.get().setServices(auxServices);
            planRepository.save(existingPlan.get());
        }
        throw new PlanNotFoundException();
    }

    public void addPlanServices(int id, List<ServiceDTO> services) {
        Optional<Plan> existingPlan = planRepository.findById(id);
        if (!existingPlan.isEmpty()) {
            for (ServiceDTO aux : services) {
                Optional<ServiceModel> existingService = serviceRepository.findById(aux.getId());
                if (!existingService.isEmpty()) {
                    existingPlan.get().getServices().add(existingService.get());
                }
                throw new ServiceNotFoundException();
            }
            planRepository.save(existingPlan.get());
        }
        throw new PlanNotFoundException();
    }

    public void removePlanServices(int id, List<ServiceDTO> services) {
        Optional<Plan> existingPlan = planRepository.findById(id);
        if (!existingPlan.isEmpty()) {
            for (ServiceDTO aux : services) {
                Optional<ServiceModel> existingService = serviceRepository.findById(aux.getId());
                if (!existingService.isEmpty()) {
                    existingPlan.get().getServices().remove(existingService.get());
                }
                throw new ServiceNotFoundException();
            }
            planRepository.save(existingPlan.get());
        }
        throw new PlanNotFoundException();
    }
}
