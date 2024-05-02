package org.aibles.java.demologin.usecase;

import lombok.RequiredArgsConstructor;
import org.aibles.java.demologin.dto.response.LoginResponse;
import org.aibles.java.demologin.exception.CustomerNotFoundException;
import org.aibles.java.demologin.exception.InvalidLoginException;
import org.aibles.java.demologin.model.Customer;
import org.aibles.java.demologin.repository.CustomerRepository;
import org.aibles.java.demologin.service.JWTService;
import org.aibles.java.demologin.service.RedisService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class LoginUseCase {

    private final CustomerRepository customerRepository;
    private final JWTService jwtService;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;
    //TODO: decode request
    //Todo: giải mã
        public LoginResponse invoke(String username, String password) throws CustomerNotFoundException, InvalidLoginException {
            Optional<Customer> customerOptional = customerRepository.findByUsername(username);
            if (customerOptional.isEmpty()) {
                throw new CustomerNotFoundException("Customer not found");
            }
            Customer customer = customerOptional.get();

            if (!passwordEncoder.matches(password, customer.getPassword())) {
                throw new InvalidLoginException("Invalid password");
            }

            AbstractMap.SimpleEntry<String, String> tokens = jwtService.generateAccessAndRefreshToken(customer);
            redisService.saveToken(customer.getUsername(), tokens);
            redisService.saveToken(username, tokens);

            return new LoginResponse(tokens.getKey(), tokens.getValue(), jwtService.getJwtExpirationMs(), jwtService.getJwtRefreshExpirationMs());
        }
    }

