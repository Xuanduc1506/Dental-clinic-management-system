package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaboDTO {

    private Long laboId;

    @NotBlank(groups = {LaboDTO.Create.class, LaboDTO.Update.class})
    private String laboName;

    @NotBlank(groups = {LaboDTO.Create.class, LaboDTO.Update.class})
    private String phone;

    private Boolean isDeleted;

    private Integer totalMoney;

    public LaboDTO(Long laboId, String laboName, String phone, Integer totalMoney) {
        this.laboId = laboId;
        this.laboName = laboName;
        this.phone = phone;
        this.totalMoney = totalMoney;
    }

    public interface Create {
    }

    public interface Update {
    }
}
