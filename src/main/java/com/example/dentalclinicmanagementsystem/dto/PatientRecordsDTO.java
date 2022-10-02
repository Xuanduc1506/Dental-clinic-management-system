package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

@Data
public class PatientRecordsDTO {

    private Long patientRecordId;

    private String reason;

    private String diagnostic;

    private String causal;

    private java.sql.Date date;

    private String treatment;

    private Integer totalCost;

    private Integer realCost;

    private String marrowRecord;

    private Integer debit;

    private String note;

    private String patientId;

    private Long specimenId;

    private Long userId;

    private Long serviceId;

    private String prescription;

    private Long preRecordId;

}
