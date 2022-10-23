package com.example.dentalclinicmanagementsystem.dto.exception;

import lombok.Data;

@Data
public class BadRequestExceptionDTO {

    private String message;

    private String fieldName;

}
