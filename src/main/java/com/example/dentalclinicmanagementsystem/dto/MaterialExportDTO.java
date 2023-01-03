package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialExportDTO {

    private Long materialExportId;

    @NotNull(groups = {MaterialExportDTO.Create.class, MaterialExportDTO.Update.class})
    private Long materialId;

    @Positive
    @NotNull(groups = {MaterialExportDTO.Create.class, MaterialExportDTO.Update.class})
    private Integer amount;

    @NotNull(groups = {MaterialExportDTO.Create.class, MaterialExportDTO.Update.class})
    private Long patientRecordId;

    @Positive
    @NotNull(groups = {MaterialExportDTO.Create.class, MaterialExportDTO.Update.class})
    private Integer unitPrice;

    private String materialName;

    private LocalDate date;

    private String patientName;

    private Boolean isDelete;

    public MaterialExportDTO(Long materialExportId, Long materialId, Integer amount, Long patientRecordId, Integer unitPrice, String materialName, LocalDate date) {
        this.materialExportId = materialExportId;
        this.materialId = materialId;
        this.amount = amount;
        this.patientRecordId = patientRecordId;
        this.unitPrice = unitPrice;
        this.materialName = materialName;
        this.date = date;
    }

    public MaterialExportDTO(Long materialExportId, Long materialId, Integer amount, Long patientRecordId, Integer unitPrice, String materialName, LocalDate date, String patientName) {
        this.materialExportId = materialExportId;
        this.materialId = materialId;
        this.amount = amount;
        this.patientRecordId = patientRecordId;
        this.unitPrice = unitPrice;
        this.materialName = materialName;
        this.date = date;
        this.patientName = patientName;
    }

    public interface Create {
    }

    public interface Update {
    }
}
