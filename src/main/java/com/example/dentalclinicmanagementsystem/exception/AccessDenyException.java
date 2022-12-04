package com.example.dentalclinicmanagementsystem.exception;

import lombok.Data;

@Data
public class AccessDenyException extends RuntimeException{

    private String entityName;

    public AccessDenyException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }
}
