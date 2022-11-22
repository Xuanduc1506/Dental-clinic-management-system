package com.example.dentalclinicmanagementsystem.dto.exception;

import lombok.Data;

@Data
public class AccessDenyExceptionDTO {

    private String message;

    private String entityName;
}
