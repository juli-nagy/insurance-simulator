package com.insurancesimulator.insurance.model;

import com.insurancesimulator.insurance.model.response.CashWithdrawResponse;
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
@Table(name = "car_insurance")
@EqualsAndHashCode(callSuper = true)
public class CarInsurance extends Insurance {

    private BigDecimal carCost;

    @Override
    public void setPremiumPayment(BigDecimal premiumPayment) {
        super.setPremiumPayment(BigDecimal.valueOf(400));
    }

    @Override
    public CashWithdrawResponse withdraw(BigDecimal cashValue) {
        BigDecimal balance = this.getBalance();
        if (cashValue.compareTo(balance) > 0) {
            return processWithdrawal(balance, BigDecimal.ZERO);
        } else {
            return processWithdrawal(cashValue, balance.subtract(cashValue));
        }
    }
}
