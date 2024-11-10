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
@Table(name = "car_insurance")
@EqualsAndHashCode(callSuper = true)
public class CarInsurance extends Insurance{
    private double carCost;
}
