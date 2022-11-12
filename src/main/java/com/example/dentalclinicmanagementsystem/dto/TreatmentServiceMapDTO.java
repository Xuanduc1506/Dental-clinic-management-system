package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

@Data
public class TreatmentServiceMapDTO {

    private Long treatmentServiceMapId;

    private String treatmentId;

    private String serviceId;

    private Integer currentPrice;

    private Integer discount;

    private Long startRecordId;
}
