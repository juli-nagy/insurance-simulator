package com.insurancesimulator.insurance.model.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CashWithdrawResponse {

    private String message;

    public CashWithdrawResponse(BigDecimal cashWithdrawn, BigDecimal balance) {
        this.message = String.format(
            "Cash value of %.3f is withdrawn successfully. Your balance is %.3f",
            cashWithdrawn.doubleValue(),
            balance.doubleValue());
    }
}
