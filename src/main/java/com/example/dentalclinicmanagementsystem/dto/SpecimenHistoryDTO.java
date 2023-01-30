package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
public class SpecimenHistoryDTO {

    private Long specimenHistoryId;

    private Long specimenId;

    private LocalDate receiveDate;

    private LocalDate deliveryDate;

    private LocalDate usedDate;

    private Integer amount;

    private Integer unitPrice;

    @Length(max = 255)
    private String description;

}
