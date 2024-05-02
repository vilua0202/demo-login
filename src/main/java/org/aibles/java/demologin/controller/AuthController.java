package org.aibles.java.demologin.controller;

import org.aibles.java.demologin.dto.request.LoginRequest;
import org.aibles.java.demologin.dto.response.BaseResponse;
import org.aibles.java.demologin.dto.response.LoginResponse;
import org.aibles.java.demologin.usecase.LoginUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;

@RequestMapping("/api/v1/customer")
@RestController
public class AuthController {

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<?>> login(@Validated @RequestBody LoginRequest loginRequest) {
        LoginResponse loginData = loginUseCase.invoke(loginRequest.getUsername(), loginRequest.getPassword());
        BaseResponse<?> baseResponse = new BaseResponse<>(HttpStatus.OK.value(), Timestamp.from(Instant.now()), loginData);
        return ResponseEntity.ok(baseResponse);
    }
}
