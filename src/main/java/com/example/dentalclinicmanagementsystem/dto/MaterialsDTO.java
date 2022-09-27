package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

@Data
public class MaterialsDTO {

    private Long materialId;

    private String materialName;

    private String unit;

    private Integer amount;

    private Integer price;

}
