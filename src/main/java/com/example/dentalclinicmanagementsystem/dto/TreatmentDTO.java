package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDTO {

    private Long treatmentId;

    private Long patientId;

    private String patientName;

    private String phone;

    private Integer totalPrice;

    private Integer totalDiscount;

    private Integer realCost;

    private List<TreatmentServiceMapDTO> treatmentServiceMapDTOList;

    public TreatmentDTO(Long treatmentId, Long patientId, String patientName, String phone, Long totalPrice, Long totalDiscount, Long realCost) {
        this.treatmentId = treatmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.phone = phone;
        this.totalPrice = totalPrice.intValue();
        this.totalDiscount = totalDiscount.intValue();
        this.realCost = realCost.intValue();
    }
}
