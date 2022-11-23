package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.TreatmentServiceMapDTO;
import com.example.dentalclinicmanagementsystem.entity.TreatmentServiceMap;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreatmentServiceMapMapper extends EntityMapper<TreatmentServiceMap, TreatmentServiceMapDTO> {

    TreatmentServiceMap toEntity(TreatmentServiceMapDTO treatmentServiceMapDTO);

    TreatmentServiceMapDTO toDto(TreatmentServiceMap treatmentServiceMap);
}
