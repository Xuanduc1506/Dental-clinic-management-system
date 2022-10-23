package com.example.dentalclinicmanagementsystem.exception;

import lombok.Data;

@Data
public class TokenException extends RuntimeException{

    public TokenException(String message) {
        super(message);
    }
}
