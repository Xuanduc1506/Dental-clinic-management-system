package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDetailDTO implements Comparable<IncomeDetailDTO>{

    private String name;

    private String date;

    private Integer price;

    public IncomeDetailDTO(String name, Long price) {
        this.name = name;
        this.price = price.intValue();
    }

    public IncomeDetailDTO(String name, LocalDate date, Integer price) {
        this.name = name;
        this.date = date.toString();
        this.price = price;
    }

    @Override
    public int compareTo(IncomeDetailDTO incomeDetailDTO) {
        if (getDate() == null || incomeDetailDTO.getDate() == null) {
            return 0;
        }
        return getDate().compareTo(incomeDetailDTO.getDate());
    }
}
