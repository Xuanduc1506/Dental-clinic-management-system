package com.example.dentalclinicmanagementsystem.mapper.exception;

import com.example.dentalclinicmanagementsystem.dto.exception.UsingEntityExceptionDTO;
import com.example.dentalclinicmanagementsystem.exception.UsingEntityException;
import com.example.dentalclinicmanagementsystem.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsingEntityExceptionMapper extends EntityMapper<UsingEntityException, UsingEntityExceptionDTO> {

    UsingEntityException toEntity(UsingEntityExceptionDTO usingEntityExceptionDTO);

    UsingEntityExceptionDTO toDto(UsingEntityException usingEntityException);
}
