package com.insurancesimulator.insurance.model.request;

import lombok.Data;

@Data
public class CashWithdrawRequest {
    private Long insuranceId;
    private Double cashValue;
}
