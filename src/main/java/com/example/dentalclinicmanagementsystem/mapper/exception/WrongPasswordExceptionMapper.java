package com.example.dentalclinicmanagementsystem.mapper.exception;

import com.example.dentalclinicmanagementsystem.dto.exception.WrongPasswordExceptionDTO;
import com.example.dentalclinicmanagementsystem.exception.WrongPasswordException;
import com.example.dentalclinicmanagementsystem.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WrongPasswordExceptionMapper extends EntityMapper<WrongPasswordException, WrongPasswordExceptionDTO> {

    WrongPasswordException toEntity(WrongPasswordExceptionDTO wrongPasswordExceptionDTO);

    WrongPasswordExceptionDTO toDto(WrongPasswordException wrongPasswordException);
}
