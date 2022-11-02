package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SpecimensDTO {
    private Long specimenId;

    private String specimenName;

    private LocalDate receiveDate;

    private LocalDate deliveryDate;

    private Integer amount;

    private Integer price;

    private Long laboId;

}
