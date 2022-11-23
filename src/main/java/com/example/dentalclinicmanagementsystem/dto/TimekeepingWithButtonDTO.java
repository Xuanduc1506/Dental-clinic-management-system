package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class TimekeepingWithButtonDTO {

    private Page<TimekeepingDTO> timekeepingDTOS;

    private Boolean checkinEnable;

    private Boolean checkoutEnable;

    private Integer workDay;

}
