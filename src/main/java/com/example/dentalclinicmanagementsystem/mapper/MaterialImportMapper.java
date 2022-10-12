package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.MaterialImportDTO;
import com.example.dentalclinicmanagementsystem.entity.MaterialImport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialImportMapper extends EntityMapper<MaterialImport, MaterialImportDTO> {
    MaterialImport toEntity(MaterialImportDTO materialImportDTO);

    MaterialImportDTO toDto(MaterialImport materialImport);
}
