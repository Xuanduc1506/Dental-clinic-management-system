package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.SpecimensDTO;
import com.example.dentalclinicmanagementsystem.entity.Specimen;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecimenMapper extends EntityMapper<Specimen, SpecimensDTO>{
    Specimen toEntity(SpecimensDTO specimensDTO);

    SpecimensDTO toDto(Specimen specimen);
}
