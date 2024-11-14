package com.insurancesimulator.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class entity) {
        super(String.format("%s entity was not found in DB", entity.getSimpleName()));
    }
}