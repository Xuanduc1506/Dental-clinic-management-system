package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyDTO {
    private Long notifyId;
    private Long treatmentId;
    private Boolean isRead;

    private String patientName;

}
