package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("SELECT p FROM Permission p JOIN RolePermissionMap rpm ON p.permissionId = rpm.permissionId " +
            "WHERE rpm.roleId = :roleId")
    Set<Permission> findAllByRoleId(@Param("roleId") Long roleId);
}
