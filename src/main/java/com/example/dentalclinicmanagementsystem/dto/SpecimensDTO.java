package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecimensDTO {
    private Long specimenId;

    private String specimenName;

    private LocalDate receiveDate;

    private LocalDate deliveryDate;

    private Integer amount;

    private Integer price;

    private Long laboId;

    private String patientName;

}
