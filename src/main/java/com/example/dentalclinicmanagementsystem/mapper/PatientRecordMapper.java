package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.PatientRecordDTO;
import com.example.dentalclinicmanagementsystem.entity.PatientRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientRecordMapper extends EntityMapper<PatientRecord, PatientRecordDTO> {

    PatientRecord toEntity(PatientRecordDTO patientRecordDTO);

    PatientRecordDTO toDto(PatientRecord patientRecord);
}
