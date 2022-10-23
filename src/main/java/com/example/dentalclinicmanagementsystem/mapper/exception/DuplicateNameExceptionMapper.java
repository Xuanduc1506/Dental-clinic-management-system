package com.example.dentalclinicmanagementsystem.mapper.exception;

import com.example.dentalclinicmanagementsystem.dto.exception.DuplicateNameExceptionDTO;
import com.example.dentalclinicmanagementsystem.exception.DuplicateNameException;
import com.example.dentalclinicmanagementsystem.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DuplicateNameExceptionMapper extends EntityMapper<DuplicateNameException, DuplicateNameExceptionDTO> {

    DuplicateNameException toEntity(DuplicateNameExceptionDTO duplicateNameExceptionDTO);

    DuplicateNameExceptionDTO toDto(DuplicateNameException duplicateNameException);
}
