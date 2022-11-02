package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.RoleDTO;
import com.example.dentalclinicmanagementsystem.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<Role, RoleDTO> {

    Role toEntity(RoleDTO roleDTO);

    RoleDTO toDto(Role role);
}
