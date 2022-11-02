package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.LaboDTO;
import com.example.dentalclinicmanagementsystem.entity.Labo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LaboMapper extends EntityMapper<Labo, LaboDTO> {

    Labo toEntity(LaboDTO laboDTO);

    LaboDTO toDto(Labo labo);
}
