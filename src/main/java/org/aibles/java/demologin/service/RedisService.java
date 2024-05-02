package org.aibles.java.demologin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);
    @Value("${lua.app.expirationMs}")
    private Long jwtExpirationMs;
    @Value("${lua.app.refreshExpirationMs}")
    private Long jwtRefreshExpirationMs;


    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToken(String username, AbstractMap.SimpleEntry<String, String> tokens) {
        // Kiểm tra và xóa token trước khi thêm token mới
        String accessTokenKey = "ACCESS_TOKEN_" + username;
        String refreshTokenKey = "REFRESH_TOKEN_" + username;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(accessTokenKey)) && Boolean.TRUE.equals(redisTemplate.hasKey(refreshTokenKey))) {
            // Token trước đó vẫn còn tồn tại
            logger.info("Deleting expired tokens for user: {}", username);
            redisTemplate.delete(accessTokenKey);
            redisTemplate.delete(refreshTokenKey);
        }

        // Thêm token mới vào Redis
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        ops.set(accessTokenKey, tokens.getKey(), jwtExpirationMs, TimeUnit.MILLISECONDS);
        ops.set(refreshTokenKey, tokens.getValue(), jwtRefreshExpirationMs, TimeUnit.MILLISECONDS);
    }


    public String retrieveToken(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void deleteToken(String key) {
        redisTemplate.delete(key);
    }
}
