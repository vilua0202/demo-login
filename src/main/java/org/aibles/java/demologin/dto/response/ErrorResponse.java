package org.aibles.java.demologin.dto.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private String field;
    private String message;

    public ErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }
}

