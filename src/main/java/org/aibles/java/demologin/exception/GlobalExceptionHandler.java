package org.aibles.java.demologin.exception;

import org.aibles.java.demologin.dto.response.BaseResponse;
import org.aibles.java.demologin.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        BaseResponse<Void> baseResponse = new BaseResponse<>(HttpStatus.NOT_FOUND.value(), Timestamp.from(Instant.now()));
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = ex.getBindingResult()
                .getAllErrors().stream()
                .map(error -> new ErrorResponse(((FieldError) error).getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        BaseResponse<Void> response = new BaseResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                Timestamp.from(Instant.now()),
                errors
        );
        return ResponseEntity.badRequest().body(response);

    }
        @ExceptionHandler(InvalidLoginException.class)
        public ResponseEntity<BaseResponse<Void>> handleInvalidLoginException (InvalidLoginException ex){
            BaseResponse<Void> baseResponse = new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), Timestamp.from(Instant.now()));
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<BaseResponse<Void>> handleException (Exception ex){
            BaseResponse<Void> baseResponse = new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Timestamp.from(Instant.now()), null);
            return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
