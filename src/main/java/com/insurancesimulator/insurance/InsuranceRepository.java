package com.insurancesimulator.insurance;

import com.insurancesimulator.insurance.model.Insurance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends
    JpaRepository<Insurance, Long> {

    List<Insurance> findAllByCustomerId(Long customerId);

}