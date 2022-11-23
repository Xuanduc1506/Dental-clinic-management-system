package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "treatment_service_map")
public class TreatmentServiceMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treatment_service_map_id")
    private Long treatmentServiceMapId;

    @Column(name = "treatment_id")
    private Long treatmentId;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "current_price")
    private Integer currentPrice;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "start_patient_record_id")
    private Long startRecordId;
}
