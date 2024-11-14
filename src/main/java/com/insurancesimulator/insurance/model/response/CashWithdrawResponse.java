package com.insurancesimulator.insurance.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CashWithdrawResponse {
    String message;

    public CashWithdrawResponse(Double cashWithdrawn, Double balance) {
        this.message = String.format(
            "Cash value of %f is withdrawn successfully. Your balance is %f",
            cashWithdrawn,
            balance);
    }
}
