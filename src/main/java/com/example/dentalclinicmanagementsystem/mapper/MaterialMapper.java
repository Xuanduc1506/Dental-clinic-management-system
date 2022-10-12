package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.MaterialDTO;
import com.example.dentalclinicmanagementsystem.entity.Material;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialMapper extends EntityMapper<Material, MaterialDTO> {

    Material toEntity(MaterialDTO materialDTO);

    MaterialDTO toDto(Material material);
}
