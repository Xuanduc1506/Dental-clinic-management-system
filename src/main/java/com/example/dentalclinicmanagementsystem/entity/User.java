package com.example.dentalclinicmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "birthdate")
    private String birthdate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "enable")
    private Boolean enable;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "role_permission_map",
            joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id", referencedColumnName = "permission_id") }
    )
    private Set<Permission> permissions = new HashSet<>();

}
