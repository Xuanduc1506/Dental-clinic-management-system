package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRecordDTO {

    private Long patientRecordId;

    @Length(max = 255, groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    @NotBlank(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private String reason;

    @Length(max = 255, groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    @NotBlank(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private String diagnostic;

    @Length(max = 255, groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    @NotBlank(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private String causal;

    @NotNull(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private LocalDate date;

    @Length(max = 255, groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    @NotBlank(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private String treatment;

    @Length(max = 255, groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private String marrowRecord;

    private String note;

    private Long treatmentId;

    private Long userId;

    @NotNull(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private List<ServiceDTO> serviceDTOS;

    private String prescription;

    private String laboName;

    private String serviceName;

    private Boolean isDeleted;

    private List<MaterialExportDTO> materialExportDTOs;

    public interface Create {
    }

    public interface Update {
    }
}
