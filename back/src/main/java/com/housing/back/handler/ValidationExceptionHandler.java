package com.housing.back.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.housing.back.dto.res.ResponseDto;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class })
    public ResponseEntity<ResponseDto> validationExceptionHandler(Exception e) {
        System.out.println("validationExceptionHandler : " + e.toString());
        return ResponseDto.ValidationFailed();
    }
}
