package com.example.dentalclinicmanagementsystem.exception;

import lombok.Data;

@Data
public class UsingEntityException extends RuntimeException{

    private String entityName;

    public UsingEntityException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }
}
