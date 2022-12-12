package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "specimen_history")
public class SpecimenHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specimen_history_id")
    private Long specimenHistoryId;

    @Column(name = "specimen_id")
    private Long specimenId;

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

    @Column(name = "description")
    private String description;

}
