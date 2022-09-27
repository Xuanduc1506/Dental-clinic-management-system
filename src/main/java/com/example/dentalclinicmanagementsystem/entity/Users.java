package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "birthdate")
    private java.sql.Date birthdate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "role_id")
    private Long roleId;

}
