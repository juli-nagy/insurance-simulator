package com.insurancesimulator.insurance.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.insurancesimulator.config.Activatable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "insurance")
@SuperBuilder
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CarInsurance.class, name = "car"),
    @JsonSubTypes.Type(value = LifeInsurance.class, name = "life")
})
public abstract class Insurance implements Activatable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long insuranceId;
    protected double coverageAmount;
    protected double balance;
    @JoinColumn(name = "customer_id")
    protected Long customerId;
    protected double premiumPayment;
    protected boolean isActive = true;

    protected Insurance() {}

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