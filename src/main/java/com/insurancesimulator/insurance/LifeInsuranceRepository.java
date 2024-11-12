package com.insurancesimulator.insurance;

import com.insurancesimulator.insurance.model.LifeInsurance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LifeInsuranceRepository extends JpaRepository<LifeInsurance, Long> {
    List<LifeInsurance> findAllByCustomer_CustomerId(Long customerId);
}