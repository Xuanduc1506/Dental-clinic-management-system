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

    private LocalDate createdDate;

    @NotNull(groups = {SpecimensDTO.Create.class, SpecimensDTO.Update.class})
    private Long patientRecordId;

    private String patientName;
    private Long patientId;

    private Boolean isDeleted;

    private boolean buttonUseEnable;

    private boolean buttonReportEnable;

    private Boolean checked;

    private String statusChange;

    public SpecimensDTO(Long specimenId, String specimenName,LocalDate createdDate, LocalDate receiveDate, LocalDate deliveryDate, LocalDate usedDate, Integer amount, Integer unitPrice, Long laboId, Integer status, Long serviceId, String serviceName, String laboName) {
        this.specimenId = specimenId;
        this.specimenName = specimenName;
        this.createdDate = createdDate;
        this.receiveDate = receiveDate;
        this.deliveryDate = deliveryDate;
        this.usedDate = usedDate;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.laboId = laboId;
        this.status = status;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.laboName = laboName;
    }

    public SpecimensDTO(Long specimenId, String specimenName, LocalDate receiveDate, LocalDate deliveryDate, Integer amount, Integer unitPrice, Long laboId, Long patientRecordId, String patientName, Integer status) {
        this.specimenId = specimenId;
        this.specimenName = specimenName;
        this.receiveDate = receiveDate;
        this.deliveryDate = deliveryDate;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.laboId = laboId;
        this.patientRecordId = patientRecordId;
        this.patientName = patientName;
        this.status = status;
    }

    public SpecimensDTO(Long specimenId, String specimenName, LocalDate receiveDate, LocalDate deliveryDate, Integer amount, Integer unitPrice, Long laboId, Integer status, Long serviceId, String serviceName, Long patientRecordId, String patientName, String laboName, LocalDate usedDate, Long patientId) {
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
        this.patientId = patientId;
    }

    public SpecimensDTO(Long specimenId, String specimenName, Integer amount, Integer unitPrice, Long serviceId, String serviceName, String patientName, Integer status, Long laboId, Long patientRecordId, LocalDate receiveDate, LocalDate deliveryDate, LocalDate usedDate) {
        this.specimenId = specimenId;
        this.specimenName = specimenName;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.patientName = patientName;
        this.status = status;
        this.laboId = laboId;
        this.patientRecordId = patientRecordId;
        this.receiveDate = receiveDate;
        this.deliveryDate = deliveryDate;
        this.usedDate = usedDate;
    }

    public interface Create {
    }

    public interface Update {
    }

}
