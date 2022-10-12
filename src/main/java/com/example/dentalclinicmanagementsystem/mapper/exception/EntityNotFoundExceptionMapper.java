package com.example.dentalclinicmanagementsystem.mapper.exception;

import com.example.dentalclinicmanagementsystem.dto.exception.EntityNotFoundExceptionDTO;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityNotFoundExceptionMapper extends EntityMapper<EntityNotFoundException, EntityNotFoundExceptionDTO> {

    EntityNotFoundException toEntity(EntityNotFoundExceptionDTO entityNotFoundExceptionDTO);

    EntityNotFoundExceptionDTO toDto(EntityNotFoundException entityNotFoundException);

}
