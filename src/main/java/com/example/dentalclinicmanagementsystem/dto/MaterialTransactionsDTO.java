package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

@Data
public class MaterialTransactionsDTO {

    private Long materialTransactionId;

    private Long materialId;

    private java.sql.Date date;

    private Integer amount;

    private Byte isBuy;

    private String patientCode;

}
