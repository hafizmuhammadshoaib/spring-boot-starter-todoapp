package com.example.TodoApp.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),ex.getLocalizedMessage(),request.getDescription(false));
        return new ResponseEntity<Object>(customErrorDetails,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,WebRequest request){
        CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<Object>(customErrorDetails,HttpStatus.NOT_FOUND);
    }
}
