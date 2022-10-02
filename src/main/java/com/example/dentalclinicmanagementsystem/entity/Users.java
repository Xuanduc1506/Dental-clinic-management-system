package com.example.dentalclinicmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "birthdate")
    private java.sql.Date birthdate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "role_id")
    private Long roleId;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "role_permission_map",
            joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id", referencedColumnName = "permission_id") }
    )
    private Set<Permission> roles = new HashSet<>();

}
