package com.insurancesimulator.insurance.model;

import com.insurancesimulator.insurance.model.response.CashWithdrawResponse;
import com.insurancesimulator.exceptions.OnlyCashOutException;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
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
    public void setPremiumPayment(BigDecimal premiumPayment) {
        super.setPremiumPayment(BigDecimal.valueOf(150));
    }

    @Override
    public CashWithdrawResponse withdraw(BigDecimal cashValue) {
        if (cashValue.compareTo(this.coverageAmount) != 0) {
            throw new OnlyCashOutException("Inaccessible operation for Life Insurance");
        } else {
            this.deactivate();
            return processWithdrawal(cashValue, balance.subtract(cashValue));
        }
    }
}
