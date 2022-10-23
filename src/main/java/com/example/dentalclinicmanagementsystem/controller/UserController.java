package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.constant.PermissionConstant;
import com.example.dentalclinicmanagementsystem.dto.UserDTO;
import com.example.dentalclinicmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.USER_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.USER_WRITE + "\")")
    @GetMapping("get_list_users")
    public ResponseEntity<Page<UserDTO>> getListUsers(@RequestParam(required = false, defaultValue = "") String username,
                                                      @RequestParam(required = false, defaultValue = "") String phone,
                                                      @RequestParam(required = false, defaultValue = "") String roleName,
                                                      Pageable pageable) {
        return ResponseEntity.ok().body(userService.getListUsers(username, phone, roleName, pageable));
    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.USER_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.USER_WRITE + "\")")
    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getDetailUser(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getDetailUser(id));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.USER_WRITE + "\")")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Validated(UserDTO.Create.class) @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(userService.registerUser(userDTO));
    }

    @PreAuthorize("hasAuthority(\"" + PermissionConstant.USER_READ + "\") or hasAnyAuthority(\"" + PermissionConstant.USER_WRITE + "\")")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@NotNull @PathVariable Long id,
                                              @Validated(UserDTO.Update.class) @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(userService.updateUser(id, userDTO));
    }

    @PreAuthorize("hasAnyAuthority(\"" + PermissionConstant.USER_WRITE + "\")")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@NotNull @PathVariable Long id) {

        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("change_password/{id}")
    public ResponseEntity<Void> changePassword(@NotNull @PathVariable Long id,
                                               @RequestParam String oldPassword,
                                               @RequestParam String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok().build();
    }
}
