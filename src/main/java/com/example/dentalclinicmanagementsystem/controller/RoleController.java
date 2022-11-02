package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.RoleDTO;
import com.example.dentalclinicmanagementsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("get_list_roles")
    public ResponseEntity<List<RoleDTO>> getListRoles(@RequestParam(required = false,defaultValue = "") String roleName) {

        return ResponseEntity.ok().body(roleService.getListRoles(roleName));

    }
}
