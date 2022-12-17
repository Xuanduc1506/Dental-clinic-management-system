package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecimensDTO {

    private Long specimenId;

    @Length(max = 255, groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    @NotBlank(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private String specimenName;

    private LocalDate receiveDate;

    private LocalDate deliveryDate;

    private LocalDate usedDate;

    @Positive
    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private Integer amount;

    @Positive
    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private Integer unitPrice;

    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private Long laboId;

    private Integer status;

    private Long serviceId;

    private String serviceName;

    private String laboName;

    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private Long patientRecordId;

    private String patientName;

    private Boolean isDeleted;

    private boolean buttonUseEnable;

    private boolean buttonReportEnable;

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

    public SpecimensDTO(Long specimenId, String specimenName, LocalDate receiveDate, LocalDate deliveryDate, Integer amount, Integer unitPrice, Long laboId, Integer status, Long serviceId, String serviceName, Long patientRecordId, String patientName, String laboName, LocalDate usedDate) {
        this.specimenId = specimenId;
        this.specimenName = specimenName;
        this.receiveDate = receiveDate;
        this.deliveryDate = deliveryDate;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.laboId = laboId;
        this.status = status;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.patientRecordId = patientRecordId;
        this.patientName = patientName;
        this.laboName = laboName;
        this.usedDate = usedDate;
    }

    public interface Create {
    }

    public interface Update {
    }

}
