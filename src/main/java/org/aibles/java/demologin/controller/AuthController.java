package org.aibles.java.demologin.controller;

import jakarta.validation.Valid;
import org.aibles.java.demologin.dto.request.LoginRequest;
import org.aibles.java.demologin.dto.response.CustomResponse;
import org.aibles.java.demologin.dto.response.LoginData;
//import org.aibles.java.demologin.dto.response.LoginResponse;
import org.aibles.java.demologin.exception.CustomerNotFoundException;
import org.aibles.java.demologin.exception.InvalidLoginException;
import org.aibles.java.demologin.usecase.LoginUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.aibles.java.demologin.dto.response.ErrorResponse;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Check if username or password is missing
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty() ||
                loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Username and password must not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            LoginData loginData = loginUseCase.invoke(loginRequest.getUsername(), loginRequest.getPassword());
            CustomResponse customResponse = new CustomResponse("success", System.currentTimeMillis(), loginData);
            return ResponseEntity.ok(customResponse);
        } catch (CustomerNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Customer not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (InvalidLoginException ex) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("HttpStatus", HttpStatus.BAD_REQUEST.value());
        errorBody.put("code", "invalid_request");
        errorBody.put("timestamp", System.currentTimeMillis());

        List<Map<String, String>> errors = ex.getBindingResult()
                .getAllErrors().stream()
                .map(this::mapError)
                .collect(Collectors.toList());

        errorBody.put("error", errors);

        return ResponseEntity.badRequest().body(errorBody);
    }
    private Map<String, String> mapError(ObjectError error) {
        Map<String, String> errorMap = new HashMap<>();
        if (error instanceof FieldError) {
            errorMap.put("field", ((FieldError) error).getField());
            errorMap.put("message", error.getDefaultMessage());
        } else {
            errorMap.put("message", error.getDefaultMessage());
        }
        return errorMap;
    }

}
