package com.insurancesimulator.insurance.model.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CashWithdrawResponse {

    String message;

    public CashWithdrawResponse(BigDecimal cashWithdrawn, BigDecimal balance) {
        this.message = String.format(
            "Cash value of %f is withdrawn successfully. Your balance is %f",
            cashWithdrawn.doubleValue(),
            balance.doubleValue());
    }
}
