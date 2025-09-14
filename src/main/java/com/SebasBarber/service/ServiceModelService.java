package com.SebasBarber.service;

import com.SebasBarber.DTO.ServiceDTO;
import com.SebasBarber.DTO.UtilDTO;
import com.SebasBarber.exception.ServiceException;
import com.SebasBarber.exception.ServiceNotFoundException;
import com.SebasBarber.exception.UserNotFoundException;
import com.SebasBarber.model.Plan;
import com.SebasBarber.model.ServiceModel;
import com.SebasBarber.repository.PlanRepository;
import com.SebasBarber.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceModelService {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    UtilDTO utilDTO;

    @Autowired
    PlanRepository planRepository;

    public void createService(ServiceModel service) {
        service.setIsActive(true);
        serviceRepository.save(service);
    }

    public Optional<ServiceDTO> getServiceById(int id) {
        return serviceRepository.findById(id).map(service -> utilDTO.mapServiceToServiceDTO(service));
    }

    public List<ServiceDTO> getServices() {
        return serviceRepository.findAll().stream()
                .map(service -> utilDTO.mapServiceToServiceDTO(service))
                .toList();
    }

    public void updateService(int id, ServiceModel service) {
        Optional<ServiceModel> existingService = serviceRepository.findById(id);
        if (!existingService.isEmpty()) {
            ServiceModel tempService = existingService.get();
            tempService.setDuration(service.getDuration());
            tempService.setDescription(service.getDescription());
            tempService.setPrice(service.getPrice());
            serviceRepository.save(tempService);
        }
        throw new ServiceNotFoundException();
    }

    public void activateService(int id) {
        Optional<ServiceModel> existingService = serviceRepository.findById(id);
        if (!existingService.isEmpty()) {
            existingService.get().setIsActive(true);
            serviceRepository.save(existingService.get());
        }
        throw new ServiceNotFoundException();
    }

    public void deactivateService(int id) {
        Optional<ServiceModel> existingService = serviceRepository.findById(id);
        if (!existingService.isEmpty()) {
            existingService.get().setIsActive(false);
            serviceRepository.save(existingService.get());
        }
        throw new ServiceNotFoundException();
    }

    public void deleteService(int id) {
        Optional<ServiceModel> existingService = serviceRepository.findById(id);
        if (!existingService.isEmpty()) {
            planRepository.deleteServiceFromPlans(id);
            serviceRepository.delete(existingService.get());
        }
        throw new ServiceNotFoundException();
    }
}
