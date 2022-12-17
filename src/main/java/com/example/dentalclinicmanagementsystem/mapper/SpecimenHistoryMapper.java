package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.SpecimenHistoryDTO;
import com.example.dentalclinicmanagementsystem.entity.SpecimenHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecimenHistoryMapper extends EntityMapper<SpecimenHistory, SpecimenHistoryDTO> {

    SpecimenHistory toEntity(SpecimenHistoryDTO specimenHistoryDTO);

    SpecimenHistoryDTO toDto(SpecimenHistory specimenHistory);
}
