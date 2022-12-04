package com.example.dentalclinicmanagementsystem.dto;

import com.example.dentalclinicmanagementsystem.entity.Permission;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;

    @NotBlank(groups = {UserDTO.Create.class, UserDTO.Update.class})
    private String fullName;

    @NotBlank(groups = {UserDTO.Login.class})
    private String userName;

    @NotBlank(groups = {UserDTO.Login.class,UserDTO.Create.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(groups = {UserDTO.Create.class, UserDTO.Update.class})
    private String birthdate;

    @NotBlank(groups = {UserDTO.Create.class, UserDTO.Update.class})
    private String phone;

    @NotNull(groups = {UserDTO.Create.class, UserDTO.Update.class})
    private Integer salary;

    @NotNull(groups = {UserDTO.Create.class, UserDTO.Update.class})
    private Long roleId;

    private Boolean enable;

    private String roleName;

    private Set<Permission> permissions = new HashSet<>();

    @NotBlank(groups = {UserDTO.Create.class, UserDTO.Update.class})
    @Email
    private String email;


    public UserDTO(Long userId, String fullName, String userName, String birthdate, String phone, Long roleId, String roleName,Integer salary, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.userName = userName;
        this.birthdate = birthdate;
        this.phone = phone;
        this.roleId = roleId;
        this.roleName = roleName;
        this.salary = salary;
        this.email = email;
    }

    public interface Create {
    }

    public interface Update {
    }

    public interface Login {
    }

    public interface ForgotPassword {
    }
}
