package org.aibles.java.demologin.usecase;

import org.aibles.java.demologin.dto.response.LoginResponse;
import org.aibles.java.demologin.exception.CustomerNotFoundException;
import org.aibles.java.demologin.exception.InvalidLoginException;
import org.aibles.java.demologin.model.Customer;
import org.aibles.java.demologin.repository.CustomerRepository;
import org.aibles.java.demologin.service.JWTService;
import org.aibles.java.demologin.service.RedisService;
import org.aibles.java.demologin.service.TokenService;
import org.aibles.java.demologin.util.EncryptionUtils;
import org.aibles.java.demologin.util.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Optional;

@Service
public class LoginUseCase {

    private final CustomerRepository customerRepository;
    private final EncryptionUtils encryptionUtils;
    private final JWTService jwtService;
    private final RedisService redisService;

    public LoginUseCase(CustomerRepository customerRepository, EncryptionUtils encryptionUtils, JWTService jwtService, RedisService redisService) {
        this.customerRepository = customerRepository;
        this.encryptionUtils = encryptionUtils;
        this.jwtService = jwtService;
        this.redisService = redisService;
    }

    public LoginResponse invoke(String username, String password) {
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found");
        }

        Customer customer = customerOptional.get();
        if (!encryptionUtils.match(password, customer.getPassword())) {
            throw new InvalidLoginException("Invalid password");
        }

        AbstractMap.SimpleEntry<String, String> tokens = jwtService.generateAccessAndRefreshToken(customer);

        redisService.saveToken(customer.getUsername(), tokens);

        redisService.saveToken(username, tokens);

        return new LoginResponse(tokens.getKey(), tokens.getValue(), jwtService.getJwtExpirationMs(), jwtService.getJwtRefreshExpirationMs()/* additional details like token expiry etc. */);
    }
}
