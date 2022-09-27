package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "specimens")
public class Specimens {
    @Id
    @Column(name = "specimen_id")
    private Long specimenId;

    @Column(name = "speciment_name")
    private String specimentName;

    @Column(name = "receive_date")
    private java.sql.Date receiveDate;

    @Column(name = "delivery_date")
    private java.sql.Date deliveryDate;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Integer price;

    @Column(name = "labo_id")
    private Long laboId;


}
