package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class MaterialDTO {

    private Long materialId;

    @Length(max = 255, groups = {MaterialDTO.Create.class, MaterialDTO.Update.class})
    @NotBlank(groups = {MaterialDTO.Create.class, MaterialDTO.Update.class})
    private String materialName;

    @Length(max = 45, groups = {MaterialDTO.Create.class, MaterialDTO.Update.class})
    @NotBlank(groups = {MaterialDTO.Create.class, MaterialDTO.Update.class})
    private String unit;

    private Integer amount;

    @Positive
    @NotNull(groups = {MaterialDTO.Create.class, MaterialDTO.Update.class})
    private Integer price;

    public interface Create {
    }

    public interface Update {
    }
}
