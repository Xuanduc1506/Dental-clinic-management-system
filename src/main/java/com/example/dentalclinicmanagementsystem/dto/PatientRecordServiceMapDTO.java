package com.example.dentalclinicmanagementsystem.dto;

public class PatientRecordServiceMapDTO {
    private Long patientRecordServiceMapId;
    private Long patientRecordId;
    private Long serviceId;

    public Long getPatientRecordServiceMapId() {
        return this.patientRecordServiceMapId;
    }

    public void setPatientRecordServiceMapId(Long patientRecordServiceMapId) {
        this.patientRecordServiceMapId = patientRecordServiceMapId;
    }

    public Long getPatientRecordId() {
        return this.patientRecordId;
    }

    public void setPatientRecordId(Long patientRecordId) {
        this.patientRecordId = patientRecordId;
    }

    public Long getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
