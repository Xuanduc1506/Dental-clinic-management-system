package com.example.dentalclinicmanagementsystem.mapper.exception;

import com.example.dentalclinicmanagementsystem.dto.exception.UsingEntityExceptionDTO;
import com.example.dentalclinicmanagementsystem.exception.AccessDenyException;
import com.example.dentalclinicmanagementsystem.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsingEntityExceptionMapper extends EntityMapper<AccessDenyException, UsingEntityExceptionDTO> {

    AccessDenyException toEntity(UsingEntityExceptionDTO usingEntityExceptionDTO);

    UsingEntityExceptionDTO toDto(AccessDenyException accessDenyException);
}
