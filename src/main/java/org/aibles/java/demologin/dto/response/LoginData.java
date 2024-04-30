package org.aibles.java.demologin.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginData {

    private String access_token;
    private String refresh_token;
    private long access_token_expiration;
    private long refresh_token_expiration;

    public LoginData(String access_token, String refresh_token, long access_token_expiration, long refresh_token_expiration) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.access_token_expiration = access_token_expiration;
        this.refresh_token_expiration = refresh_token_expiration;
    }
    public LoginData() {}

    @Override
    public String toString() {
        return "data: {" +
                "access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", access_token_expiration=" + access_token_expiration +
                ", refresh_token_expiration=" + refresh_token_expiration +
                '}';
    }
}
