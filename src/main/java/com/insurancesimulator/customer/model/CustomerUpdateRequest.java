package com.insurancesimulator.customer.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CustomerUpdateRequest {
    String name;
    LocalDate birthDate;
}
