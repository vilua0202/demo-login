package org.aibles.java.demologin.dto.response;


public class ErrorResponse {

    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and setters omitted for brevity
}

