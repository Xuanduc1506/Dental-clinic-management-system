package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimekeepingDTO {

    private Long timekeepingId;

    private Long userId;

    private LocalDateTime timeCheckin;

    private LocalDateTime timeCheckout;

    private String fullName;

    public TimekeepingDTO(Long timekeepingId, Long userId, LocalDateTime timeCheckin, LocalDateTime timeCheckout) {
        this.timekeepingId = timekeepingId;
        this.userId = userId;
        this.timeCheckin = timeCheckin;
        this.timeCheckout = timeCheckout;
    }
}
