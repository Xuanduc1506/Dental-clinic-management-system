package com.example.dentalclinicmanagementsystem.exception;

import lombok.Data;

@Data
public class DuplicateNameException extends RuntimeException{

    private String entityName;

    public DuplicateNameException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }
}
