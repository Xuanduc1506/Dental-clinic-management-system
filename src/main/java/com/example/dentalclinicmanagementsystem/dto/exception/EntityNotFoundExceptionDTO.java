package com.example.dentalclinicmanagementsystem.dto.exception;

import lombok.Data;

@Data
public class EntityNotFoundExceptionDTO {

    private String entityField;

    private String entityName;

    private String message;
}
