package com.example.dentalclinicmanagementsystem.dto;

import lombok.Data;

@Data
public class NotifyDTO {
    private Long notifyId;
    private Long treatmentId;
    private Boolean isRead;
}
