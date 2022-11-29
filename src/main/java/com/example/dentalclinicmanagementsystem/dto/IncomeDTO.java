package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class IncomeDTO {

    private Long totalIncome;

    List<IncomeDetailDTO> incomeDetailDTOS;

}
