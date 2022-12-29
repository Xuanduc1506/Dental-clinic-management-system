package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

@Data
public class TotalIncomeDTO {

    private String date;

    private Long income;

    private Long netIncome;

    private Long totalSpend;

}
