package com.insurancesimulator.config;

public interface Activatable {
    void deactivate(); // Method to deactivate the instance
    boolean isActive(); // Method to check if the instance is active
}