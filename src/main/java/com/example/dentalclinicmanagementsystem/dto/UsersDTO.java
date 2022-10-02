package com.example.dentalclinicmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsersDTO {

    private Long userId;

    private String userName;

    @JsonIgnore
    private String password;

    private LocalDateTime birthdate;

    private String phone;

    @JsonIgnore
    private Integer salary;

    private Long roleId;

}
