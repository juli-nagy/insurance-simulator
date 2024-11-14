package com.insurancesimulator.customer.model;

import com.insurancesimulator.config.Activatable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer implements Activatable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String name;
    private LocalDate birthDate;
    protected boolean isActive = true;

    @Override
    public void deactivate() {
        this.isActive = false;
    }

    @Override
    public void activate() {
        this.isActive = true;
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }
}
