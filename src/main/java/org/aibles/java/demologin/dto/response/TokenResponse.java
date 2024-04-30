package org.aibles.java.demologin.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public TokenResponse(String accessToken, String refreshToken, long accessTokenExpiration, long refreshTokenExpiration) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", accessTokenExpiration=" + accessTokenExpiration +
                ", refreshTokenExpiration=" + refreshTokenExpiration +
                ", token='" + token + '\'' +
                '}';
    }
}

