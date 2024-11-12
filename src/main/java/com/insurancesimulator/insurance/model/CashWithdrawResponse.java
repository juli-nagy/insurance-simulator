package com.insurancesimulator.insurance.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CashWithdrawResponse {
    Double cashWithdrawn;
    Double balance;
}
