package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "role_permission_map")
public class RolePermissionMap {
    @Id
    @Column(name = "role_permission__map_id")
    private Long rolePermissionMapId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permission_id")
    private Long permissionId;

}
