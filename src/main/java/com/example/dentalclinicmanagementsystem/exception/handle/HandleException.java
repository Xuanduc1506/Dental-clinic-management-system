package com.example.dentalclinicmanagementsystem.exception.handle;

import com.example.dentalclinicmanagementsystem.dto.exception.*;
import com.example.dentalclinicmanagementsystem.exception.*;
import com.example.dentalclinicmanagementsystem.mapper.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class HandleException extends ResponseEntityExceptionHandler {

    @Autowired
    private EntityNotFoundExceptionMapper entityNotFoundExceptionMapper;

    @Autowired
    private WrongPasswordExceptionMapper wrongPasswordExceptionMapper;

    @Autowired
    private DuplicateNameExceptionMapper duplicateNameExceptionMapper;

    @Autowired
    private TokenExceptionMapper tokenExceptionMapper;

    @Autowired
    private UsingEntityExceptionMapper usingEntityExceptionMapper;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<EntityNotFoundExceptionDTO> handleEntityNotFoundException(
            EntityNotFoundException entityNotFoundException) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(entityNotFoundExceptionMapper.toDto(entityNotFoundException));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<WrongPasswordExceptionDTO> handleWrongPasswordException(
            WrongPasswordException wrongPasswordException) {

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(wrongPasswordExceptionMapper.toDto(wrongPasswordException));
    }

    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<DuplicateNameExceptionDTO> handleDuplicateNamesException(
            DuplicateNameException duplicateNameException) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(duplicateNameExceptionMapper.toDto(duplicateNameException));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<BadRequestExceptionDTO> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            BadRequestExceptionDTO badRequestExceptionDTO = new BadRequestExceptionDTO();
            badRequestExceptionDTO.setFieldName(((FieldError) error).getField());
            badRequestExceptionDTO.setMessage(error.getDefaultMessage());

            errors.add(badRequestExceptionDTO);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<TokenExceptionDTO> handleTokenException(
            TokenException tokenException) {

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(tokenExceptionMapper.toDto(tokenException));
    }

    @ExceptionHandler(AccessDenyException.class)
    public ResponseEntity<UsingEntityExceptionDTO> handleUsingEntityException(
            AccessDenyException accessDenyException) {

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(usingEntityExceptionMapper.toDto(accessDenyException));
    }
}
