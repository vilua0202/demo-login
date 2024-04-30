package org.aibles.java.demologin.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String code;
    private long timestamp;
   // private List<FieldError> error = new ArrayList<>();

    public ErrorResponse(int status, String code) {
        this.status = status;
        this.code = code;
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorResponse(int status, String code, long timestamp, List<FieldError> error) {
        this.status = status;
        this.code = code;
        this.timestamp = timestamp;
       // this.error = error;
    }

//    public void addFieldError(String field, String message) {
//        FieldError error = new FieldError(field, message);
//        this.error.add(error);
//    }

    public static class FieldError {
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
