package com.example.dentalclinicmanagementsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_code")
    private String patientCode;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "birthdate")
    private java.sql.Date birthdate;

    @Column(name = "gender")
    private Byte gender;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "body_prehistory")
    private String bodyPrehistory;

    @Column(name = "teeth_prehistory")
    private String teethPrehistory;

}
