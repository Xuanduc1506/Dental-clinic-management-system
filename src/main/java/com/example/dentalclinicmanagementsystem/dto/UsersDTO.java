package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsersDTO {

    private Long userId;

    private String userName;

    private LocalDateTime birthdate;

    private String phone;

    private Integer salary;

    private Long roleId;

}
