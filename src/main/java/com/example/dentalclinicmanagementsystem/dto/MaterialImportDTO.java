package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialImportDTO {

    private Long materialImportId;

    @NotNull(groups = {MaterialImportDTO.Create.class, MaterialImportDTO.Update.class})
    private Long materialId;

    @NotNull(groups = {MaterialImportDTO.Create.class, MaterialImportDTO.Update.class})
    private Date date;

    @NotNull(groups = {MaterialImportDTO.Create.class, MaterialImportDTO.Update.class})
    private Integer amount;

    @NotBlank(groups = {MaterialImportDTO.Create.class, MaterialImportDTO.Update.class})
    private String supplyName;

    private String materialName;

    public MaterialImportDTO(Long materialImportId, Date date, Integer amount, String supplyName, String materialName) {
        this.materialImportId = materialImportId;
        this.date = date;
        this.amount = amount;
        this.supplyName = supplyName;
        this.materialName = materialName;
    }

    public interface Create {
    }

    public interface Update {
    }
}
