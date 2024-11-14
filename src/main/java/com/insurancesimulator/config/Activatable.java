package com.insurancesimulator.config;

public interface Activatable {
    void activate();
    void deactivate();
    boolean isActive();
}