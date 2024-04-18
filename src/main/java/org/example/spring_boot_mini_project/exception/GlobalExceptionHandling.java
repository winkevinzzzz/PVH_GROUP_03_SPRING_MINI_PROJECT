package org.example.spring_boot_mini_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandling {
//    in case id negative openapi not show but in console in IDE show error

    @ExceptionHandler(FindNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(FindNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Bad Request");
        problemDetail.setStatus(404);
//        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleUserNotFoundException(MethodArgumentNotValidException e) {
        HashMap<String, String> errors = new HashMap<>();
        for (FieldError FieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(FieldError.getField(), FieldError.getDefaultMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Invalid field");
        problemDetail.setTitle("Not Found");
        problemDetail.setStatus(404);
        problemDetail.setProperty("errors", LocalDateTime.now());
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail methodValidationExceptionHandler(HandlerMethodValidationException exception) {
        HashMap<String, String> errors = new HashMap<>();
        for (var parameterError : exception.getAllValidationResults()) {
            final String parameterName = parameterError.getMethodParameter().getParameterName();
            for (var error : parameterError.getResolvableErrors()) {
                errors.put(parameterName, error.getDefaultMessage());
            }
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Validation field");
        problemDetail.setType(URI.create("http://localhost:8080/api/"));
        problemDetail.setTitle("Bad Request");
        problemDetail.setStatus(404);
        problemDetail.setProperty("errors", LocalDateTime.now());
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }
}
