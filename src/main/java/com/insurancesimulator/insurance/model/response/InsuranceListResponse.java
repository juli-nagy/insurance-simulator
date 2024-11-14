package com.insurancesimulator.insurance.model.response;

import com.insurancesimulator.insurance.model.Insurance;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InsuranceListResponse {
    private String message;
    private int count;
    private List<Insurance> insurances;
}

