package com.SebasBarber.repository;

import com.SebasBarber.model.Plan;
import com.SebasBarber.model.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

    List<Plan> findByServices(ServiceModel service);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM plan_service WHERE service_id = :serviceId", nativeQuery = true)
    void deleteServiceFromPlans(@Param("serviceId") int serviceId);
}
