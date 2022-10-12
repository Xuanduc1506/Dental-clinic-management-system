package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(name = "patients_code")
    private String patientsCode;

    @Column(name = "date")
    private java.sql.Date date;

    @Column(name = "status")
    private String status;

    @Column(name = "booked")
    private Byte booked;

}
