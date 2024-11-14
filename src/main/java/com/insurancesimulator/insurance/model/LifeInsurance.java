package com.insurancesimulator.insurance.model;

import com.insurancesimulator.insurance.model.response.CashWithdrawResponse;
import exceptions.OnlyCashOutException;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "life_insurance")
@EqualsAndHashCode(callSuper = true)

public class LifeInsurance extends Insurance{
    private int policyTermInYears;

    @Override
    public void setPremiumPayment(double premiumPayment) {
        super.setPremiumPayment(150);
    }

    @Override
    public CashWithdrawResponse withdraw(Double cashValue) {
        if (cashValue != this.coverageAmount) {
            throw new OnlyCashOutException("Inaccessible operation for Life Insurance");
        }
        else {
            this.deactivate();
            return processWithdrawal(cashValue, balance - cashValue);
        }
    }
}
