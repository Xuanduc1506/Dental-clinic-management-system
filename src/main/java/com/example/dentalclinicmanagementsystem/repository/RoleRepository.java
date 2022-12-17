package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r JOIN User u ON r.roleId = u.role.roleId " +
            "WHERE u.userName = :username")
    Role findRoleNameByUser(@Param("username")String username);

    List<Role> findAllByRoleNameContainingIgnoreCase(String name);

    Role findByRoleId(Long roleId);
}
