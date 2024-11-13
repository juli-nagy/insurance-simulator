package com.insurancesimulator.insurance.model;

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
@Table(name = "car_insurance")
@EqualsAndHashCode(callSuper = true)
public class CarInsurance extends Insurance{
    private double carCost;

    @Override
    public void setPremiumPayment(double premiumPayment) {
        super.setPremiumPayment(400);
    }

    @Override
    public CashWithdrawResponse withdraw(Double cashValue) {
        double balance = this.getBalance();
        if (cashValue > balance) {
            return processWithdrawal(balance, 0.0);
        } else {
            return processWithdrawal(cashValue, balance - cashValue);
        }
    }
}
