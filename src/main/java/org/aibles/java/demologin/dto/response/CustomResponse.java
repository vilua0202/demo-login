package org.aibles.java.demologin.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponse {
    private String code;
    private long timestamp;
    private LoginData data;

    public CustomResponse(String code, long timestamp, LoginData data) {
        this.code = code;
        this.timestamp = timestamp;
        this.data = data;
    }
}