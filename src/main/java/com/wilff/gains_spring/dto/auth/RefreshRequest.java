package com.wilff.gains_spring.dto.auth;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}
