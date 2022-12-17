package com.example.dentalclinicmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Role  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinTable(
            name = "role_permission_map",
            joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id", referencedColumnName = "permission_id") }
    )
    private Set<Permission> permissions = new HashSet<>();
}
