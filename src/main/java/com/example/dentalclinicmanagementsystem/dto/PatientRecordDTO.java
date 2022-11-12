package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRecordDTO {

    private Long patientRecordId;

    @NotBlank(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private String reason;

    @NotBlank(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private String diagnostic;

    @NotBlank(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private String causal;

    private LocalDate date;

    @NotBlank(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private String treatment;

    private String marrowRecord;

    private String note;

    private Long treatmentId;

    private Long userId;

    @NotNull(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private List<ServiceDTO> serviceDTOS;

    private String prescription;

    private String laboName;

    private String serviceName;

    public interface Create {
    }

    public interface Update {
    }
}
