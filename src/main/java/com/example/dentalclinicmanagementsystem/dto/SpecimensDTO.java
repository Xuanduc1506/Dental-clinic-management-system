package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecimensDTO {
    private Long specimenId;

    @NotBlank(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private String specimenName;

    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private LocalDate receiveDate;

    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private LocalDate deliveryDate;

    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private Integer amount;

    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private Integer unitPrice;

    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private Long laboId;

    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private Long patientRecordId;

    private String patientName;

    private Boolean isDeleted;

    public SpecimensDTO(Long specimenId, String specimenName, LocalDate receiveDate, LocalDate deliveryDate, Integer amount, Integer unitPrice, Long laboId, Long patientRecordId, String patientName) {
        this.specimenId = specimenId;
        this.specimenName = specimenName;
        this.receiveDate = receiveDate;
        this.deliveryDate = deliveryDate;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.laboId = laboId;
        this.patientRecordId = patientRecordId;
        this.patientName = patientName;
    }

    public interface Create {
    }

    public interface Update {
    }

}
