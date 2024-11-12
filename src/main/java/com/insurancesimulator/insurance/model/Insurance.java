package com.insurancesimulator.insurance.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.insurancesimulator.config.Activatable;
import com.insurancesimulator.customer.model.Customer;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = LifeInsurance.class, name = "lifeInsurance"),
    @JsonSubTypes.Type(value = CarInsurance.class, name = "carInsurance")
})
public abstract class Insurance implements Activatable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long insuranceId;
    protected double coverageAmount;
    protected double balance;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    protected Customer customer;
    protected double premiumPayment;
    protected boolean isActive = true;

    @Override
    public void deactivate() {
        this.isActive = false;
    }

    public abstract CashWithdrawResponse withdraw(Double cashValue);

    protected CashWithdrawResponse processWithdrawal(double amountWithdrawn, double newBalance) {
        this.updateBalance(newBalance);
        if (newBalance == 0.0) {
            this.deactivate();
        }
        return new CashWithdrawResponse(amountWithdrawn, newBalance);
    }

    public void updateBalance(Double balance) {
        if (balance != null && !Objects.equals(this.balance, balance)) {
            this.balance = balance;
        }
    }

}