package com.github.machadojoaovictor.link_shortener.exception;

import com.github.machadojoaovictor.link_shortener.dto.response.StandardErrorDTO;
import com.github.machadojoaovictor.link_shortener.dto.response.ValidationErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorDTO> genericHandler(Exception exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardErrorDTO err = StandardErrorDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Internal Server Error")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> resourceNotFoundHandler(ResourceNotFoundException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardErrorDTO err = StandardErrorDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Resource not found")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> methodArgumentNotValidHandler(MethodArgumentNotValidException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationErrorDTO errorDTO = ValidationErrorDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Validation error")
                .message("One or more fields are invalid. Please check the 'errors' list for details.")
                .path(request.getRequestURI())
                .build();

        List<FieldError> fieldErrorList = exception.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrorList) {
            errorDTO.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardErrorDTO> badRequestHandler(BadRequestException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO errorDTO = StandardErrorDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardErrorDTO> httpMessageNotReadableHandler(HttpMessageNotReadableException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorDTO errorDTO = StandardErrorDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Malformed Request")
                .message("Field must be a valid ISO 8601 date")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardErrorDTO> dataIntegrityViolationHandler(DataIntegrityViolationException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        StandardErrorDTO errorDTO = StandardErrorDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Resource Conflict")
                .message("The resource could not be created due to a conflict, such as a duplicate entry.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<StandardErrorDTO> uriSyntaxExceptionHandler(URISyntaxException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardErrorDTO errorDTO = StandardErrorDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Bad Request")
                .message("Invalid URL format provided.")
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<StandardErrorDTO> dataAccessExceptionHandler(DataAccessException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        StandardErrorDTO errorDTO = StandardErrorDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Service Unavailable")
                .message("The service is temporarily unavailable due to a data access issue.")
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(errorDTO);
    }


}
