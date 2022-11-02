package com.example.dentalclinicmanagementsystem.dto;

import java.time.LocalDate;
import java.util.Date;

public interface PatientRecordInterfaceDTO {

    Long getPatientRecordId();
    String getReason();
    String getDiagnostic();
    String getCausal();
    LocalDate getDate();
    String getTreatment();
    Integer getTotalCost();
    Integer getRealCost();
    String getMarrowRecord();
    Integer getDebit();
    Integer getCostIncurred();
    String getNote();
    String getPrescription();
    String getLaboName();
    String getServices();

}
