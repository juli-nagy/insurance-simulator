package com.insurancesimulator.exceptions;

public class InsuranceIsInactiveException extends RuntimeException {

    public InsuranceIsInactiveException() {
        super("Requested insurance is inactive");
    }
}