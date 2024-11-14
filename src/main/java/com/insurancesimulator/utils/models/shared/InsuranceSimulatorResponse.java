package com.insurancesimulator.utils.models.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class InsuranceSimulatorResponse {
    private String message;
    private HttpStatus status;
}