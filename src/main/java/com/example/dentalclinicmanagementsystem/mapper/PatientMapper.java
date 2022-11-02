package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.PatientDTO;
import com.example.dentalclinicmanagementsystem.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper extends EntityMapper<Patient, PatientDTO> {

    Patient toEntity(PatientDTO patientDTO);

    PatientDTO toDto(Patient patient);
}

