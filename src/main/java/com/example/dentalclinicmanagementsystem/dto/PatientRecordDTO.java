package com.example.dentalclinicmanagementsystem.dto;

import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.exception.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
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

    @NotNull(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private Integer totalCost;

    @NotNull(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private Integer realCost;

    private String marrowRecord;

    @NotNull(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private Integer debit;

    private Integer costIncurred;

    private String note;

    private Long patientId;

    private Long userId;

    @NotNull(groups = {PatientRecordDTO.Create.class, PatientRecordDTO.Update.class})
    private List<Long> serviceId;

    private String prescription;

    private Long preRecordId;

    private String laboName;

    private String serviceName;

    public PatientRecordDTO(Long patientRecordId, String reason, String diagnostic, String causal, LocalDate date, String treatment, Integer totalCost, Integer realCost, String marrowRecord, Integer debit, String note, String prescription, String laboName, String serviceName) {
        this.patientRecordId = patientRecordId;
        this.reason = reason;
        this.diagnostic = diagnostic;
        this.causal = causal;
        this.date = date;
        this.treatment = treatment;
        this.totalCost = totalCost;
        this.realCost = realCost;
        this.marrowRecord = marrowRecord;
        this.debit = debit;
        this.note = note;
        this.prescription = prescription;
        this.laboName = laboName;
        this.serviceName = serviceName;
    }

    public interface Create {
    }

    public interface Update {
    }
}
