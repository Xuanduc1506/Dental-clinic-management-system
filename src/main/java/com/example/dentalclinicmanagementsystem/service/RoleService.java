package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.dto.RoleDTO;
import com.example.dentalclinicmanagementsystem.mapper.RoleMapper;
import com.example.dentalclinicmanagementsystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    public List<RoleDTO> getListRoles(String roleName) {
        return roleMapper.toDto(roleRepository.findAllByRoleNameContainingIgnoreCase(roleName));
    }
}
