package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyDTO {
    private Long notifyId;
    private Long treatmentId;
    private Boolean isRead;
    private LocalDateTime time;

    private String patientName;

    private String phone;



}
