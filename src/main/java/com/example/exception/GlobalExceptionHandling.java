package com.example.exception;

import com.example.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handlingglobalexception(Exception e){

        ErrorMessage error = ErrorMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("You are doing something wrong").build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
