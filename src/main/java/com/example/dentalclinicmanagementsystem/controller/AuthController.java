package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.UserDTO;
import com.example.dentalclinicmanagementsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Validated(UserDTO.Login.class) @RequestBody UserDTO userDTO) throws AuthenticationException {
        return authService.login(userDTO);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<Void> resetPassword(@RequestParam String username) throws AuthenticationException {
        authService.resetPassword(username);
        return ResponseEntity.ok().build();

    }
}
