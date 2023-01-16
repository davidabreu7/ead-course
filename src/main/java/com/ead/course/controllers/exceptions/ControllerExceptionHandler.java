package com.ead.course.controllers.exceptions;

import com.ead.course.exceptions.DatabaseIntegrityException;
import com.ead.course.exceptions.FieldException;
import com.ead.course.exceptions.ResourceNotFoundException;
import com.ead.course.exceptions.SubscriptionException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FieldException.class)
    public ResponseEntity<StandardError> resourceNotFound(FieldException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DatabaseIntegrityException.class)
    public ResponseEntity<StandardError> database(DatabaseIntegrityException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        return getValidationErrorResponseEntity(e, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationError> database(ConstraintViolationException e, HttpServletRequest request) {
        return getMongoValidationErrorResponseEntity(e, request);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<StandardError> database(DuplicateKeyException e, HttpServletRequest request) {
        return getMongoKeyValidationErrorResponseEntity(e, request);
    }

    @ExceptionHandler(SubscriptionException.class)
    public ResponseEntity<StandardError> database(SubscriptionException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, request, HttpStatus.CONFLICT);
    }
//    @ExceptionHandler(WebClientResponseException.class)
//    public ResponseEntity<StandardError> webClientResponseException(WebClientResponseException e, HttpServletRequest request) {
//        return getStandardErrorResponseEntity(e, request, Objects.requireNonNull(HttpStatus.resolve(e.getStatusCode().value())));
//    }

    private ResponseEntity<StandardError> getStandardErrorResponseEntity(RuntimeException e, HttpServletRequest request, HttpStatus status) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    private ResponseEntity<ValidationError> getValidationErrorResponseEntity(MethodArgumentNotValidException e, HttpServletRequest request) {

        ValidationError err = new ValidationError();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .forEach(fieldError -> err.getFieldErrors().add(fieldError));
        return ResponseEntity.status(status).body(err);
    }

    private ResponseEntity<ValidationError> getMongoValidationErrorResponseEntity(ConstraintViolationException e, HttpServletRequest request) {

        ValidationError err = new ValidationError();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        e.getConstraintViolations()
                .stream()
                .map(error -> new FieldError(error.getPropertyPath().toString(), error.getMessage()))
                .forEach(fieldError -> err.getFieldErrors().add(fieldError));

        return ResponseEntity.status(status).body(err);
    }

    private ResponseEntity<StandardError> getMongoKeyValidationErrorResponseEntity(DuplicateKeyException e, HttpServletRequest request) {

        StandardError err = new StandardError();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setMessage(e.getCause().getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}


