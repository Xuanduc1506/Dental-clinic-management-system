package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimekeepingDTO {

    private Long timekeepingId;

    private Long userId;

    private LocalDateTime timeCheckin;

    private LocalDateTime timeCheckout;

}
