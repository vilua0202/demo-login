package org.aibles.java.demologin.service;

import org.aibles.java.demologin.model.Customer;
import org.aibles.java.demologin.util.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private final StringRedisTemplate redisTemplate;
    private final JWTService jwtService;

    public TokenService(StringRedisTemplate redisTemplate, JWTService jwtService) {
        this.redisTemplate = redisTemplate;
        this.jwtService = jwtService;
    }
}
