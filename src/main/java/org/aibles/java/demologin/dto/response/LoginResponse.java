package org.aibles.java.demologin.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;

}
