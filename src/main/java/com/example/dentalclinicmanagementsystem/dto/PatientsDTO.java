package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

@Data
public class PatientsDTO {

    private String patientCode;

    private String patientName;

    private java.sql.Date birthdate;

    private Byte gender;

    private String address;

    private String phone;

    private String email;

    private String bodyPrehistory;

    private String teethPrehistory;

}
