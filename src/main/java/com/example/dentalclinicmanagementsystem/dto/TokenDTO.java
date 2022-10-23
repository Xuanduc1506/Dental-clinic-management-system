package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

@Data
public class TokenDTO {

    private String jwt;

    private String role;

}
