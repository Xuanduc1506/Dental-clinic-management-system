package com.example.dentalclinicmanagementsystem.mapper.exception;

import com.example.dentalclinicmanagementsystem.dto.exception.AccessDenyExceptionDTO;
import com.example.dentalclinicmanagementsystem.exception.AccessDenyException;
import com.example.dentalclinicmanagementsystem.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsingEntityExceptionMapper extends EntityMapper<AccessDenyException, AccessDenyExceptionDTO> {

    AccessDenyException toEntity(AccessDenyExceptionDTO accessDenyExceptionDTO);

    AccessDenyExceptionDTO toDto(AccessDenyException accessDenyException);
}
