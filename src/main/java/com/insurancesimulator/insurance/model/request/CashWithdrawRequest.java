package com.insurancesimulator.insurance.model.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CashWithdrawRequest {

    private BigDecimal cashValue;
}
