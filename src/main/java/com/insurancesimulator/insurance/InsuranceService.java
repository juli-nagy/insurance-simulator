package com.insurancesimulator.insurance;

import com.insurancesimulator.insurance.model.CashWithdrawResponse;
import com.insurancesimulator.insurance.model.Insurance;
import exceptions.EntityNotFoundException;
import exceptions.InsuranceIsInactiveException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final LifeInsuranceRepository lifeInsuranceRepository;
    private final CarInsuranceRepository carInsuranceRepository;
    public void addInsurance(Insurance insurance){
        insuranceRepository.save(insurance);
    }

    public List<Insurance> getAllInsurances(){
        List<Insurance> insurances = new ArrayList<>();
        insurances.addAll(lifeInsuranceRepository.findAll());
        insurances.addAll(carInsuranceRepository.findAll());

        return insurances;
    }

    public List<Insurance> getAllInsurancesByCustomerId(Long customerId) {
        List<Insurance> insurances = new ArrayList<>();
        insurances.addAll(lifeInsuranceRepository.findAllByCustomer_CustomerId(customerId));
        insurances.addAll(carInsuranceRepository.findAllByCustomer_CustomerId(customerId));

        return insurances;
    }

    public void deleteInsurance(Long insuranceId) {
        boolean exists = false;

        if (lifeInsuranceRepository.existsById(insuranceId)) {
            lifeInsuranceRepository.deleteById(insuranceId);
            exists = true;
        } else if (carInsuranceRepository.existsById(insuranceId)) {
            carInsuranceRepository.deleteById(insuranceId);
            exists = true;
        }

        if (!exists) {
            throw new EntityNotFoundException(Insurance.class);
        }
    }

    @Transactional
    public CashWithdrawResponse withdraw(Long insuranceId, Double cashValue){
        Insurance insurance = findInsuranceById(insuranceId);
        if (!insurance.isActive()){
            throw new InsuranceIsInactiveException();
        }
        double balance = insurance.getBalance();
        if (cashValue > balance) {
            insurance.deactivate();
            return processWithdrawal(insurance, balance, 0.0);
        }
        else{
            return processWithdrawal(insurance, cashValue, balance-cashValue);
        }
    }

    private CashWithdrawResponse processWithdrawal(Insurance insurance, double amountWithdrawn, double newBalance) {
        insurance.updateBalance(newBalance);
        insuranceRepository.save(insurance);
        return new CashWithdrawResponse(amountWithdrawn, newBalance);
    }

    private Insurance findInsuranceById(Long insuranceId) {
        return lifeInsuranceRepository.findById(insuranceId)
            .map(Insurance.class::cast)
            .orElseGet(() -> carInsuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException(Insurance.class)));
    }
}