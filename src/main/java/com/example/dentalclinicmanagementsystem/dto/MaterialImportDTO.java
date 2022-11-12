package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialImportDTO {

    private Long materialImportId;

    @NotNull(groups = {MaterialImportDTO.Create.class, MaterialImportDTO.Update.class})
    private Long materialId;

    @NotNull(groups = {MaterialImportDTO.Create.class, MaterialImportDTO.Update.class})
    private LocalDate date;

    @NotNull(groups = {MaterialImportDTO.Create.class, MaterialImportDTO.Update.class})
    private Integer amount;

    @NotBlank(groups = {MaterialImportDTO.Create.class, MaterialImportDTO.Update.class})
    private String supplyName;

    private String materialName;

    @NotNull(groups = {MaterialImportDTO.Create.class, MaterialImportDTO.Update.class})
    private Integer totalPrice;

    private Boolean isDelete;

    public MaterialImportDTO(Long materialImportId, Long materialId, LocalDate date, Integer amount, String supplyName, String materialName, Integer totalPrice) {
        this.materialImportId = materialImportId;
        this.materialId = materialId;
        this.date = date;
        this.amount = amount;
        this.supplyName = supplyName;
        this.materialName = materialName;
        this.totalPrice = totalPrice;
    }

    public interface Create {
    }

    public interface Update {
    }
}
