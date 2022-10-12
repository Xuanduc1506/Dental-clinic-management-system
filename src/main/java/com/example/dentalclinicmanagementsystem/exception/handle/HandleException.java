package com.example.dentalclinicmanagementsystem.exception.handle;

import com.example.dentalclinicmanagementsystem.dto.exception.EntityNotFoundExceptionDTO;
import com.example.dentalclinicmanagementsystem.dto.exception.WrongPasswordExceptionDTO;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.exception.WrongPasswordException;
import com.example.dentalclinicmanagementsystem.mapper.exception.EntityNotFoundExceptionMapper;
import com.example.dentalclinicmanagementsystem.mapper.exception.WrongPasswordExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class HandleException extends ResponseEntityExceptionHandler {

    @Autowired
    private EntityNotFoundExceptionMapper entityNotFoundExceptionMapper;

    @Autowired
    private WrongPasswordExceptionMapper wrongPasswordExceptionMapper;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<EntityNotFoundExceptionDTO> handleEntityNotFoundException(
            EntityNotFoundException entityNotFoundException) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(entityNotFoundExceptionMapper.toDto(entityNotFoundException));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<WrongPasswordExceptionDTO> handleWrongPasswordException(
            WrongPasswordException wrongPasswordException){

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(wrongPasswordExceptionMapper.toDto(wrongPasswordException));
    }

//    @ExceptionHandler(RuntimeException)


}
