package com.example.dentalclinicmanagementsystem.entity;

import javax.persistence.*;

@Entity
@Table(name = "patient_record_service_map")
public class PatientRecordServiceMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_record_service_map_id")
    private Long patientRecordServiceMapId;

    @Column(name = "patient_record_id")
    private Long patientRecordId;

    @Column(name = "service_id")
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
