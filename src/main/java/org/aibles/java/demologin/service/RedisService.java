package org.aibles.java.demologin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToken(String username, AbstractMap.SimpleEntry<String, String> tokens) {
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        ops.set("ACCESS_TOKEN_" + username, tokens.getKey(), 10, TimeUnit.MINUTES); // 10 minutes expiry for access token
        ops.set("REFRESH_TOKEN_" + username, tokens.getValue(), 30, TimeUnit.DAYS); // 30 days expiry for refresh token
    }

    public String retrieveToken(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void deleteToken(String key) {
        redisTemplate.delete(key);
    }
}
