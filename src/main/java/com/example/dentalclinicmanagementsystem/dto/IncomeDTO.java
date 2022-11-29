package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class IncomeDTO {

    private Integer totalIncome;

    List<TreatmentServiceMapDTO> treatmentServiceMapDTOS;

}
