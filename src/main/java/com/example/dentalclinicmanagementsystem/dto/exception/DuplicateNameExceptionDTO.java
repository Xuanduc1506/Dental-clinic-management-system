package com.example.dentalclinicmanagementsystem.dto.exception;

import lombok.Data;

@Data
public class DuplicateNameExceptionDTO {

    private String message;

    private String entityName;
}
