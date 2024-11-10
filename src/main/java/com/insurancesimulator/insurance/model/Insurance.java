package com.insurancesimulator.insurance.model;

import com.insurancesimulator.config.Activatable;
import com.insurancesimulator.customer.model.Customer;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Id;

@MappedSuperclass
public abstract class Insurance implements Activatable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long insuranceId;
    protected double coverageAmount;
    protected double balance;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
    protected Customer customer;
    protected boolean isActive = true;

    @Override
    public void deactivate() {
        this.isActive = false;
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

}