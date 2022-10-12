package com.example.dentalclinicmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;

    @NotBlank(groups = {UserDTO.Create.class, UserDTO.Update.class})
    private String fullName;

    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(groups = {UserDTO.Create.class, UserDTO.Update.class})
    private String birthdate;

    @NotNull(groups = {UserDTO.Create.class, UserDTO.Update.class})
    private String phone;

    @NotNull(groups = {UserDTO.Create.class, UserDTO.Update.class})
    private Integer salary;

    @NotNull(groups = {UserDTO.Create.class, UserDTO.Update.class})
    private Long roleId;

    private Boolean enable;

    private String roleName;

    public UserDTO(Long userId, String fullName, String userName, String birthdate, String phone, Long roleId, String roleName) {
        this.userId = userId;
        this.fullName = fullName;
        this.userName = userName;
        this.birthdate = birthdate;
        this.phone = phone;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public interface Create {
    }

    public interface Update {
    }
}
