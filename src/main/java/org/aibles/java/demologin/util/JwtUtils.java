package org.aibles.java.demologin.util;



import java.security.Key;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;

import org.aibles.java.demologin.dto.request.LoginRequest;
import org.aibles.java.demologin.dto.response.LoginResponse;
import org.aibles.java.demologin.model.Customer;
import org.aibles.java.demologin.service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${lua.app.jwtCookieName}")
    private String jwtCookie;

    public String getJwtFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }

}

