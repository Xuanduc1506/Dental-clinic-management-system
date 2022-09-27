package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

@Data
public class ServiceDTO {

    private Long serviceId;

    private String serviceName;

    private String unit;

    private Integer marketPrice;

    private Integer price;

    private Long categoryServiceId;

}
