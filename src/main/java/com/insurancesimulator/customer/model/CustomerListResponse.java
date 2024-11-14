package com.insurancesimulator.customer.model;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerListResponse {
    private String message;
    private int count;
    private List<Customer> customers;
}
