package com.apogee.trackarea.model.data;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    private SingleUserDetails user;
    public JwtAuthenticationResponse(String accessToken){
        this.accessToken = accessToken;
    }
}
