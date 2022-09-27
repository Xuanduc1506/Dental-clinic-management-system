package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

@Data
public class SpecimensDTO {
    private Long specimenId;

    private String specimenName;

    private java.sql.Date receiveDate;

    private java.sql.Date deliveryDate;

    private Integer amount;

    private Integer price;

    private Long laboId;

}
