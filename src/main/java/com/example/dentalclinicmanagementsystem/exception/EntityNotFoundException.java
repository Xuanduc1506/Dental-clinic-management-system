package com.example.dentalclinicmanagementsystem.exception;

import lombok.Data;

@Data
public class EntityNotFoundException extends RuntimeException {

    private String entityField;

    private String entityName;

    public EntityNotFoundException(String message, String entityName, String entityField) {
        super(message);
        this.entityName = entityName;
        this.entityField = entityField;
    }
}
