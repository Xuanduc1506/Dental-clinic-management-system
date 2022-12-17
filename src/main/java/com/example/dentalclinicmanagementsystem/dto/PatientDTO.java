package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long patientId;

    @Length(max = 255, groups = {PatientDTO.Create.class, PatientDTO.Update.class})
    @NotBlank(groups = {PatientDTO.Create.class, PatientDTO.Update.class})
    private String patientName;

    @Length(max = 40, groups = {PatientDTO.Create.class, PatientDTO.Update.class})
    @NotBlank(groups = {PatientDTO.Create.class, PatientDTO.Update.class})
    private String birthdate;

    @NotNull(groups = {PatientDTO.Create.class, PatientDTO.Update.class})
    private Boolean gender;

    private String address;

    @Positive
    @Length(max = 10, groups = {PatientDTO.Create.class, PatientDTO.Update.class})
    @NotBlank(groups = {PatientDTO.Create.class, PatientDTO.Update.class})
    private String phone;

    @Length(max = 255, groups = {PatientDTO.Create.class, PatientDTO.Update.class})
    private String email;

    private String bodyPrehistory;

    private String teethPrehistory;

    private Integer status;

    private Boolean isDeleted;

    public PatientDTO(Long patientId, String patientName, String birthdate, Boolean gender, String address, String phone, String email, String bodyPrehistory, String teethPrehistory, Integer status) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.bodyPrehistory = bodyPrehistory;
        this.teethPrehistory = teethPrehistory;
        this.status = status;
    }

    public interface Create {
    }

    public interface Update {
    }
}
