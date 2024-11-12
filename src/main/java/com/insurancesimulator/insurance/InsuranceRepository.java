package com.insurancesimulator.insurance;

import com.insurancesimulator.insurance.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends
    JpaRepository<Insurance, Long> {
}