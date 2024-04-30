package com.laydowncoding.tickitecking.global.exception;

import com.laydowncoding.tickitecking.global.response.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j(topic = "GlobalExceptionHandler -> ")
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleValidationException(
            MethodArgumentNotValidException e
    ) {
        BindingResult bindingResult = e.getBindingResult();
        List<ErrorResponse> errors = new ArrayList<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.add(new ErrorResponse(error.getDefaultMessage()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleCustomRuntimeException(CustomRuntimeException e) {
        log.error("예외 발생", e);
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
