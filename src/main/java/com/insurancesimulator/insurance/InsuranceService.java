package com.insurancesimulator.insurance;

import com.insurancesimulator.insurance.model.CashWithdrawResponse;
import com.insurancesimulator.insurance.model.Insurance;
import exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;

    public void addInsurance(Insurance insurance){
        insuranceRepository.save(insurance);
    }

    public List<Insurance> getAllInsurances(){
        return insuranceRepository.findAll();
    }

    public List<Insurance> getAllInsurancesByCustomerId(Long customerId) {
        return insuranceRepository.findAllByCustomerId(customerId);
    }

    public void deleteInsurance(Long insuranceId) {
        boolean exists = false;

        if (insuranceRepository.existsById(insuranceId)) {
            insuranceRepository.deleteById(insuranceId);
            exists = true;}

        if (!exists) {
            throw new EntityNotFoundException(Insurance.class);
        }
    }

    @Transactional
    public CashWithdrawResponse withdraw(Long insuranceId, Double amount) {
        Insurance insurance = insuranceRepository.findById(insuranceId)
            .orElseThrow(() -> new EntityNotFoundException(Insurance.class));

        CashWithdrawResponse response = insurance.withdraw(amount);
        insuranceRepository.save(insurance);
        return response;
    }
}