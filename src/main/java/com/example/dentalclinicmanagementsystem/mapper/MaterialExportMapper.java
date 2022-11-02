package com.example.dentalclinicmanagementsystem.mapper;

import com.example.dentalclinicmanagementsystem.dto.MaterialExportDTO;
import com.example.dentalclinicmanagementsystem.entity.MaterialExport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialExportMapper extends EntityMapper<MaterialExport, MaterialExportDTO> {

}
