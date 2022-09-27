package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_permission_map")
public class UserPermissionMap {
    @Id
    @Column(name = "user_permission_map_id")
    private Long userPermissionMapId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "permission_id")
    private Long permissionId;

}
