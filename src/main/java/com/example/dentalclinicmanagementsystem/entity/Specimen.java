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

    @Column(name = "used_date")
    private LocalDate usedDate;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "unit_price")
    private Integer unitPrice;

    @Column(name = "patient_record_id")
    private Long patientRecordId;

    @Column(name = "labo_id")
    private Long laboId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "receive_date", insertable = false, updatable = false)
    private String tempReceiveDate;

    @Column(name = "delivery_date", insertable = false, updatable = false)
    private String tempDeliveryDate;

    @Column(name = "used_date", insertable = false, updatable = false)
    private String tempUsedDate;

    @Column(name = "amount", insertable = false, updatable = false)
    private String tempAmount;

    @Column(name = "unit_price", insertable = false, updatable = false)
    private String tempUnitPrice;

}
