package org.aibles.java.demologin.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    private int code;
    private Timestamp timestamp;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ErrorResponse> errors;

    public BaseResponse(int code, Timestamp timestamp, T data) {
        this.code = code;
        this.timestamp = timestamp;
        this.data = data;
        this.errors = null;
    }

    public BaseResponse(int code,Timestamp timestamp){
        this.code = code;
        this.timestamp = timestamp;
    }

    public BaseResponse(int code, Timestamp timestamp, List<ErrorResponse> errors) {
        this.code = code;
        this.timestamp = timestamp;
        this.errors = errors;
    }
}