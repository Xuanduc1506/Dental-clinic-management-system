package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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

    @Column(name = "status")
    private Integer status;

    @Column(name = "start_record_id")
    private Long startRecordId;

}
