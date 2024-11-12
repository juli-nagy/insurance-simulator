package com.insurancesimulator.insurance;

import com.insurancesimulator.insurance.model.CarInsurance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarInsuranceRepository extends JpaRepository<CarInsurance, Long> {
    List<CarInsurance> findAllByCustomer_CustomerId(Long customerId);
}
