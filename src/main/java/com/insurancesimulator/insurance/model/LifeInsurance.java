package com.insurancesimulator.insurance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "life_insurance")
@EqualsAndHashCode(callSuper = true)

public class LifeInsurance extends Insurance{
    private int policyTermInYears;
}
