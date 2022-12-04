package com.example.dentalclinicmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long patientId;

    @NotBlank(groups = {PatientDTO.Create.class, PatientDTO.Update.class})
    private String patientName;

    @NotBlank(groups = {PatientDTO.Create.class, PatientDTO.Update.class})
    private String birthdate;

    @NotNull(groups = {PatientDTO.Create.class, PatientDTO.Update.class})
    private Boolean gender;

    private String address;

    @NotBlank(groups = {PatientDTO.Create.class, PatientDTO.Update.class})
    private String phone;

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
