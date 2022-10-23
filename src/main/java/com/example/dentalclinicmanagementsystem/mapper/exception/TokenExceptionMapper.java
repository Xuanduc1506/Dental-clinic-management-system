package com.example.dentalclinicmanagementsystem.mapper.exception;

import com.example.dentalclinicmanagementsystem.dto.exception.TokenExceptionDTO;
import com.example.dentalclinicmanagementsystem.exception.TokenException;
import com.example.dentalclinicmanagementsystem.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenExceptionMapper extends EntityMapper<TokenException, TokenExceptionDTO> {

    TokenException toEntity(TokenExceptionDTO tokenExceptionDTO);

    TokenExceptionDTO toDto(TokenException tokenException);
}

