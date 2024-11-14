package com.insurancesimulator.insurance;

import com.insurancesimulator.insurance.model.response.CashWithdrawResponse;
import com.insurancesimulator.insurance.model.Insurance;
import com.insurancesimulator.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    public void addInsurance(Insurance insurance) {
        insuranceRepository.save(insurance);
    }

    public List<Insurance> getAllInsurances() {
        return insuranceRepository.findAll();
    }

    public List<Insurance> getAllInsurancesByCustomerId(Long customerId) {
        return insuranceRepository.findAllByCustomerId(customerId);
    }

    public void deleteInsurance(Long insuranceId) {
        if (!insuranceRepository.existsById(insuranceId)) {
            throw new EntityNotFoundException(Insurance.class);
        }
        insuranceRepository.deleteById(insuranceId);
    }

    @Transactional
    public CashWithdrawResponse withdraw(Long insuranceId, BigDecimal amount) {
        Insurance insurance = insuranceRepository.findById(insuranceId)
            .orElseThrow(() -> new EntityNotFoundException(Insurance.class));

        CashWithdrawResponse response = insurance.withdraw(amount);
        insuranceRepository.save(insurance);
        return response;
    }
}