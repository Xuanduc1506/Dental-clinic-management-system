package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "specimens")
public class Specimen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specimen_id")
    private Long specimenId;

    @Column(name = "specimen_name")
    private String specimenName;

    @Column(name = "receive_date")
    private LocalDate receiveDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Integer price;

    @Column(name = "patient_record_id")
    private Long patientRecordId;

    @Column(name = "labo_id")
    private Long laboId;


}
