package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

@Data
public class MaterialExportDTO {
    private Long materialExportId;
    private Long materialId;
    private Integer amount;
    private Long patientRecordId;
}
